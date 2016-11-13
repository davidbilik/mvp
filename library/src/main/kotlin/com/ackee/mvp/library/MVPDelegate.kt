package com.ackee.mvp.library

import android.os.Bundle

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPDelegate<out P : Presenter<Any, DataState>>: PresenterView<P> {
    override fun create(arguments: Bundle?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewCreated(view: Any) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewResumed(view: Any) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewPaused(view: Any) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewDestroyed(view: Any) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy(terminal: Boolean) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPresenter(): P {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val TAG: String = MVPDelegate::class.java.name
    }



}