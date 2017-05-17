package com.ackee.mvp.library

import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction


/**
 * Transformer that converts our observable to observable that emits "deliveries" that are containers
 * with data and view, when the view is ready (attached to presenter)
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
internal class DeliverToViewSticky<V : MvpView, T>(private val view: Observable<OptionalView<V>>, val filterOnComplete: Boolean = true) : ObservableTransformer<T, Delivery<V, T>> {

    override fun apply(observable: Observable<T>): ObservableSource<Delivery<V, T>> {
        return Observable
                .combineLatest(
                        view,
                        observable
                                .materialize()
                                .filter { notification -> !(notification.isOnComplete && filterOnComplete) },
                        BiFunction<OptionalView<V>, Notification<T>, Array<Any>> { view, notification -> arrayOf(view, notification) })
                .concatMap({ pack -> Delivery.validObservable(pack[0] as OptionalView<V>, pack[1] as Notification<T>) })

    }
}