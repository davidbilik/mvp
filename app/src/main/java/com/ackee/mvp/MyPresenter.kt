package com.ackee.mvp

import android.util.Log
import com.ackee.mvp.core.Presenter

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/
class MyPresenter : Presenter<IMyView>() {
    companion object {
        val TAG: String = MyPresenter::class.java.name
    }
    fun loadData() {
        Log.d(TAG, "loadData: ");
    }
}