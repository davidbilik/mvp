package com.ackee.mvp.library

import android.os.Bundle

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
interface PresenterView<out P : Presenter<*>> {
    fun create(arguments: Bundle?)

    fun viewCreated(view: MVPView)

    fun viewResumed(view: MVPView)

    fun viewPaused(view: MVPView)

    fun viewDestroyed(view: MVPView)

    fun destroy(terminal: Boolean)

    fun getPresenter(): P

    fun saveState(state: Bundle)

    fun restoreState(state: Bundle)
}