package com.ackee.mvp.library

import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction


/**
 * Transformer that converts our observable to observable that emits "deliveries" that are containers
 * with data and view, when the view is ready (attached to presenter). Data are delivered only once to view
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
internal class DeliverToView<V : MvpView, T>(private val view: Observable<OptionalView<V>>, val filterOnComplete: Boolean = true) : ObservableTransformer<T, Delivery<V, T>> {

    override fun apply(observable: Observable<T>): ObservableSource<Delivery<V, T>> {
        return observable
                .materialize()
                .filter { notification -> !(notification.isOnComplete && filterOnComplete) }
                .flatMap { notification ->
                    view
                            .filter { it.view != null }
                            .take(1)
                            .map { Delivery(it.view!!, notification) }
                }


    }
}