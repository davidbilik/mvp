package com.ackee.mvp.library

import android.os.Bundle
import rx.subjects.BehaviorSubject

/**
 * Base class for Presenter layer.
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
open class Presenter<V : MVPView> {

    var globalId: String = PresenterManager.add(this)

    var view: BehaviorSubject<V> = BehaviorSubject.create()
    var viewState: BehaviorSubject<ViewState> = BehaviorSubject.create()

    fun create(bundle: Bundle) {

    }

    fun viewCreated(view: V) {
        this.view.onNext(view)
    }

    fun viewResumed() {

    }

    fun viewPaused() {

    }

    fun viewDestroyed() {
        view.onNext(null)
    }

    fun destroy() {
        view.onCompleted()
        PresenterManager.remove(globalId)
    }
}