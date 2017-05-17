package com.ackee.mvp.library

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Notification
import io.reactivex.Observable
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Test DeliveryToView transformer.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
class DeliverToViewStickyTest {

    @Test
    fun deliver_to_view() {
        val view = mock<MvpView>()
        val deliverToView = DeliverToViewSticky<MvpView, Unit>(Observable.just(OptionalView(view)))
        val testObserver = Observable.just(Unit).compose(deliverToView).test()
        assertEquals(1, testObserver.events[0].size)
        assertEquals(Delivery(view, Notification.createOnNext(Unit)), testObserver.events[0][0])
    }
}