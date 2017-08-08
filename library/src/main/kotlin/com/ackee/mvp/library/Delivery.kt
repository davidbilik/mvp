package com.ackee.mvp.library

import io.reactivex.Notification
import io.reactivex.Observable

/**
 * Container for presenter-view "deliveries". Contains data and view that should receive this data.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
data class Delivery<out V : MvpView, out T>(private val view: V, private val notification: Notification<T>) {

    fun split(onNext: (V.(item: T) -> Unit)? = null,
              onError: (V.(error: Throwable) -> Unit)? = null,
              onComplete: (V.() -> Unit)? = null) {
        if (onNext != null && notification.isOnNext) {
            onNext.invoke(view, notification.value!!)
        } else if (onError != null && notification.isOnError) {
            onError.invoke(view, notification.error!!)
        } else if (onComplete != null && notification.isOnComplete) {
            onComplete.invoke(view)
        }
    }
}