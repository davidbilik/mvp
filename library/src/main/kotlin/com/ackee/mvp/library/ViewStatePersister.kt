package com.ackee.mvp.library

import android.os.Bundle
import android.os.Parcelable

/**
 * TODO add class description
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 04/05/2017
 **/
class ViewStatePersister<T : Parcelable> {
    companion object {
        val TAG: String = ViewStatePersister::class.java.name
        private val STATE_KEY: String = "state"
    }

    fun saveToBundle(bundle: Bundle, state: T) {
        bundle.putParcelable(STATE_KEY, state)
    }

    fun getFromBundle(bundle: Bundle): T? {
        return bundle.getParcelable(STATE_KEY)
    }

}