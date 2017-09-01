package com.ackee.mvp.library

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Test [DeliverToViewSticky] transformer.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
class DeliverToViewStickyTest {

    @Test
    fun deliver_to_view() {
        val view = mock<MvpView>()
        val deliverToView = DeliverToViewSticky<MvpView, Unit>(Observable.just(OptionalView(view)))
        val testObserver = Observable.fromIterable(listOf(Unit)).compose(deliverToView).test()
        assertEquals(1, testObserver.events[0].size)
        assertEquals(Delivery(view, Notification.createOnNext(Unit)), testObserver.events[0][0])
    }

    @Test
    fun deliver_to_view_only_last() {
        val view = mock<MvpView>()
        val deliverToView = DeliverToViewSticky<MvpView, Unit>(Observable.just(OptionalView(view)))
        val testObserver = Observable.fromIterable(listOf(Unit)).compose(deliverToView).test()
        assertEquals(1, testObserver.events[0].size)
        assertEquals(Delivery(view, Notification.createOnNext(Unit)), testObserver.events[0][0])
    }

    @Test
    fun deliver_to_view_null_view() {
        val subject = PublishSubject.create<OptionalView<MvpView>>()
        subject.onNext(OptionalView(null))
        val deliverToView = DeliverToViewSticky<MvpView, Unit>(subject)
        val testObserver = Observable.just(Unit).compose(deliverToView).test()
        assertEquals(0, testObserver.events[0].size)
        subject.onNext(OptionalView(mock<MvpView>()))
        assertEquals(1, testObserver.events[0].size)
    }
}