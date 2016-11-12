package com.ackee.mvp.library

import android.os.Parcelable

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
abstract class DataState : Parcelable{
    companion object {
        val TAG: String = DataState::class.java.name
    }

}