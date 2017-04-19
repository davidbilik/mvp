package com.ackee.mvp.library

import android.os.Bundle

/**
 * Generic interface for presenter creator.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/18/2017
 */
interface PresenterCreator<out P : Presenter<out MvpView>> {
    fun createPresenter(bundle: Bundle?): P
}