package com.ackee.mvp.library

import android.os.Bundle
import com.ackee.mvp.core.Presenter

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
interface PresenterView<out P : Presenter<*>> {
    fun create(arguments: Bundle?)

    fun viewCreated(view: Any)

    fun viewResumed(view: Any)

    fun viewPaused(view: Any)

    fun viewDestroyed(view: Any)

    fun destroy(terminal: Boolean)

    fun getPresenter(): P

    fun saveState(state: Bundle)

    fun restoreState(state: Bundle)
}