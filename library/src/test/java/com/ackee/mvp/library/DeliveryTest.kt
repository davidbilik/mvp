package com.ackee.mvp.library

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Notification
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Test Delivery logic.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
class DeliveryTest {

    @Test
    fun split_with_onNext() {
        val view = mock<MvpView>()
        val onNext = mock<Notification<Unit>> {
            on { isOnNext } doReturn true
            on { isOnError } doReturn false
            on { isOnComplete } doReturn false
            on { value } doReturn Unit
            on { error } doReturn Exception()
        }
        val delivery = Delivery(view, onNext)
        var asserter = false
        delivery.split({ asserter = true }, { })
        assertTrue(asserter)
    }

    @Test
    fun split_with_onError() {
        val view = mock<MvpView>()
        val onError = mock<Notification<Unit>> {
            on { isOnNext } doReturn false
            on { isOnError } doReturn true
            on { isOnComplete } doReturn false
            on { value } doReturn Unit
            on { error } doReturn Exception()
        }
        val delivery = Delivery(view, onError)
        var asserter = false
        delivery.split({ }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun split_with_onComplete() {
        val view = mock<MvpView>()
        val onCompleted = mock<Notification<Unit>> {
            on { isOnNext } doReturn false
            on { isOnError } doReturn false
            on { isOnComplete } doReturn true
            on { value } doReturn Unit
            on { error } doReturn Exception()
        }
        val delivery = Delivery(view, onCompleted)
        var asserter = false
        delivery.split({ }, { }, { asserter = true })
        assertTrue(asserter)
    }
}