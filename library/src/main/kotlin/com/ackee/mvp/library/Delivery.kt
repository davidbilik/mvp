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
            onNext.invoke(view, notification.value)
        } else if (onError != null && notification.isOnError) {
            onError.invoke(view, notification.error)
        } else if (onComplete != null && notification.isOnComplete) {
            onComplete.invoke(view)
        }
    }

    companion object {
        /**
         * Check if observable is valid (the view is attached). If it is valid, return observable
         * that emits delivery, otherwise, return empty observable.
         */
        internal fun <V : MvpView, T> validObservable(view: OptionalView<V>, notification: Notification<T>): Observable<Delivery<V, T>> {
            return if (view.view != null)
                Observable.just(Delivery(view.view, notification))
            else
                Observable.empty()
        }
    }
}