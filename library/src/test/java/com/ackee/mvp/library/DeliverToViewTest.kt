package com.ackee.mvp.library

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Test [DeliverToView] transformer.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
class DeliverToViewTest {

    @Test
    fun deliver_to_view() {
        val view = mock<MvpView>()
        val deliverToView = DeliverToView<MvpView, Int>(Observable.just(OptionalView(view)))
        val testObserver = Observable.fromIterable(listOf(1)).compose(deliverToView).test()
        assertEquals(1, testObserver.events[0].size)
        assertEquals(Delivery(view, Notification.createOnNext(1)), testObserver.events[0][0])
    }

    @Test
    fun deliver_to_view_no_events() {
        val view = mock<MvpView>()
        val deliverToView = DeliverToView<MvpView, Int>(Observable.just(OptionalView(view)))
        val testObserver = PublishSubject.create<Int>().compose(deliverToView).test()
        assertEquals(0, testObserver.events[0].size)
    }

    @Test
    fun deliver_to_null_view() {
        val subject = PublishSubject.create<OptionalView<MvpView>>()
        subject.onNext(OptionalView(null))
        val deliverToView = DeliverToView<MvpView, Int>(subject)
        val testObserver = Observable.fromIterable(listOf(1)).compose(deliverToView).test()
        assertEquals(0, testObserver.events[0].size)
        val view = mock<MvpView>()
        subject.onNext(OptionalView(view))
        assertEquals(1, testObserver.events[0].size)
        assertEquals(Delivery(view, Notification.createOnNext(1)), testObserver.events[0][0])
    }

    @Test
    fun deliver_to_view_only_actual() {
        val subject = PublishSubject.create<OptionalView<MvpView>>()
        subject.onNext(OptionalView(null))
        val deliverToView = DeliverToView<MvpView, Int>(subject)
        val testObserver = Observable.fromIterable(listOf(1, 2, 3)).compose(deliverToView).test()
        assertEquals(0, testObserver.events[0].size)
        val view = mock<MvpView>()
        subject.onNext(OptionalView(view))
        assertEquals(1, testObserver.events[0].size)
        assertEquals(Delivery(view, Notification.createOnNext(3)), testObserver.events[0][0])
    }
}