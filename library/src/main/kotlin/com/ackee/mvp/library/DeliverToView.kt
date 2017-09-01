package com.ackee.mvp.library

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Transformer that converts our observable to observable that emits "deliveries" that are containers
 * with data and view, when the view is ready (attached to presenter). Data are delivered to view
 * only once.
 *
 * When the view is attached and more than one item was emitted while the view was detached, only
 * last one is delivered.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
internal class DeliverToView<V : MvpView, T>(private val view: Observable<OptionalView<V>>,
                                             val filterOnComplete: Boolean = true) : ObservableTransformer<T, Delivery<V, T>> {

    override fun apply(observable: Observable<T>): ObservableSource<Delivery<V, T>> {
        return observable
                .materialize()
                .filter { notification -> !(notification.isOnComplete && filterOnComplete) }
                .switchMap { notification ->
                    view
                            .filter { it.view != null }
                            .take(1)
                            .map { Delivery(it.view!!, notification) }
                }
    }
}