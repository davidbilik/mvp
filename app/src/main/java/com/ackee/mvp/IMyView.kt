package com.ackee.mvp

import com.ackee.mvp.library.MvpView

/**
 * View Example
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/
interface IMyView : MvpView {
    fun showText(text: String)
}