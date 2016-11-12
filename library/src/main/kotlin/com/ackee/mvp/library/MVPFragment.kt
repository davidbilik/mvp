package com.ackee.mvp.library

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPFragment<P : Presenter<Any, DataState>> : Fragment(), PresenterView {
    val delegate = MVPDelegate<P>()

    companion object {
        val TAG: String = MVPFragment::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create(arguments)
    }

    override fun create(bundle: Bundle?) {
        delegate.onCreate(bundle)
    }
}