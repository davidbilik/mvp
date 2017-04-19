package com.ackee.mvp.library

import com.ackee.mvp.library.Delivery.Companion.validObservable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Transformer that converts our observable to observable that emits "deliveries" that are containers
 * with data and view, when the view is ready (attached to presenter)
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
internal class DeliverToView<V : MvpView, T>(private val view: Observable<OptionalView<V>>) : ObservableTransformer<T, Delivery<V, T>> {

    override fun apply(observable: Observable<T>): ObservableSource<Delivery<V, T>> {
        return observable.materialize()
                .take(1)
                .switchMap { notification -> view.concatMap { view -> validObservable(view, notification) } }
                .take(1)
    }
}