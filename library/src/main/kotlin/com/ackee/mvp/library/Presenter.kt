package com.ackee.mvp.library

import android.os.Parcelable
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Base presenter class.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/16/2017
 */
abstract class Presenter<V : MvpView, out T : Parcelable> {

    internal val viewSubject = BehaviorSubject.create<OptionalView<V>>()
    internal val disposables = CompositeDisposable()
    internal var viewDisposables = CompositeDisposable()

    /**
     * Get presenter view state to restore
     */
    open fun stateToSave(): T? {
        return null
    }

    /**
     * Attach view to presenter. Notify all observers that the view is attached.
     */
    fun attachView(view: V) {
        /**
         * View disposable needs to be created every time view is attached because if dispose() is called
         * in detachView, we cannot add new disposables to its array. Its internal state is DISPOSED and it
         * immediately dispose new disposables
         */
        viewDisposables = CompositeDisposable()
        viewSubject.onNext(OptionalView(view))
    }

    /**
     * Attach view from presenter. Notify all observers that the view is detached. Dispose of all
     * view-bound disposables
     */
    fun detachView() {
        viewSubject.onNext(OptionalView(null))
        viewDisposables.dispose()
    }

    /**
     * Called from view when it is completely destroyed and presenter is no longer needed. Clean all
     * the mess: dispose of all bound disposables, complete view subject.
     */
    fun onDestroy() {
        viewSubject.onComplete()
        disposables.dispose()
    }

    /**
     * Bind subscription (disposable) to presenter lifecycle. It will be automatically unsubscribed
     * (disposed of) when the presenter will be destroyed.
     */
    fun bind(disposable: Disposable) {
        disposables.add(disposable)
    }

    /**
     * Bind subscription (disposable) to view lifecycle. It will be automatically unsubscribed
     * (disposed of) when the view will be detached from presenter.
     */
    fun bindToView(disposable: Disposable) {
        viewDisposables.add(disposable)
    }


    /**
     * Wait until the view will be attached and run some code on it.
     */
    fun onViewReady(onViewReady: V.() -> Unit): Disposable {
        return viewIfExists()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onViewReady(it) }, Throwable::printStackTrace)
    }

    /**
     * Call [onViewReady] function whenever view is ready
     */
    fun onViewReadySticky(onViewReady: V.() -> Unit) {
        viewSubject.filter { it.view != null }
                .map { it.view!! }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onViewReady(it) }, Throwable::printStackTrace)
    }

    private fun viewIfExists(): Observable<V> {
        return viewSubject.filter { it.view != null }
                .take(1)
                .map { it.view!! }
    }


    /**
     * Deliver emitted data from [Observable] to view once it is attached.  Run [onNext], [onError] and
     * [onComplete] on view.
     */
    private fun <T> Observable<T>.deliverToViewInternal(onNext: (V.(item: T) -> Unit)? = null,
            onError: (V.(error: Throwable) -> Unit)? = null,
            onComplete: (V.() -> Unit)? = null, filterComplete: Boolean = true): Disposable {
        return compose(DeliverToView<V, T>(viewSubject, filterComplete))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it.split(onNext, onError, onComplete) })
    }

    /**
     * Deliver latest emitted data from [Observable] to view every time it is attached. Run [onNext], [onError] and
     * [onComplete] on view.
     */
    private fun <T> Observable<T>.deliverToViewStickyInternal(onNext: (V.(item: T) -> Unit)? = null,
            onError: (V.(error: Throwable) -> Unit)? = null,
            onComplete: (V.() -> Unit)? = null, filterComplete: Boolean = true): Disposable {
        return compose(DeliverToViewSticky<V, T>(viewSubject, filterComplete))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it.split(onNext, onError, onComplete) })
    }

    /**
     * Deliver latest data from [Observable] to view when it is attached. Run [onNext] and [onError]
     * on view.
     */
    fun <T> Observable<T>.deliverToView(onNext: V.(item: T) -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return deliverToViewInternal(onNext, onError)
    }

    /**
     * Deliver latest data from [Maybe] to view when it is attached. Run [onSuccess] and [onError]
     * on view.
     */
    fun <T> Maybe<T>.deliverToView(onSuccess: V.(item: T) -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return toObservable().deliverToViewInternal(onSuccess, onError)
    }

    /**
     * Deliver latest data from [Single] to view when it is attached. Run [onSuccess] and [onError]
     * on view.
     */
    fun <T> Single<T>.deliverToView(onSuccess: V.(item: T) -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return toObservable().deliverToView(onSuccess, onError)
    }

    /**
     * Deliver event from [Completable] to view when it is attached. Run [onComplete] and [onError].
     */
    fun Completable.deliverToView(onComplete: V.() -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return toObservable<Unit>().deliverToViewInternal(onError = onError, onComplete = onComplete, filterComplete = false)
    }

    /**
     * Deliver latest data from [Observable] to view when it is attached. Run [onNext] and [onError]
     * on view.
     */
    fun <T> Observable<T>.deliverToViewSticky(onNext: V.(item: T) -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return deliverToViewStickyInternal(onNext, onError)
    }

    /**
     * Deliver latest data from [Single] to view when it is attached. Run [onSuccess] and [onError]
     * on view.
     */
    fun <T> Single<T>.deliverToViewSticky(onSuccess: V.(item: T) -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return toObservable().deliverToViewStickyInternal(onSuccess, onError)
    }

    /**
     * Deliver latest data from [Maybe] to view when it is attached. Run [onSuccess] and [onError]
     * on view.
     */
    fun <T> Maybe<T>.deliverToViewSticky(onSuccess: V.(item: T) -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return toObservable().deliverToViewStickyInternal(onSuccess, onError)
    }

    /**
     * Deliver event from [Completable] to view when it is attached. Run [onComplete] and [onError].
     */
    fun Completable.deliverToViewSticky(onComplete: V.() -> Unit,
            onError: (V.(error: Throwable) -> Unit)? = null): Disposable {
        return toObservable<Unit>().deliverToViewStickyInternal(onError = onError, onComplete = onComplete, filterComplete = false)
    }
}