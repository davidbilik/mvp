package com.ackee.mvp.library

import android.os.Bundle

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPDelegate<P : Presenter<Any, DataState>>{
    companion object {
        val TAG: String = MVPDelegate::class.java.name
    }

    fun onCreate(arguments: Bundle?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}