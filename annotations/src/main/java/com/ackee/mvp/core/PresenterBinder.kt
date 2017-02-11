package com.ackee.mvp.core

import com.ackee.mvp.core.Presenter

/**
 * Abstract class that binds presenter to view

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 17/11/16
 **/
abstract class PresenterBinder {
    companion object {
        val TAG: String = PresenterBinder::class.java.name
    }

    abstract fun createPresenter(): Presenter<*>
}