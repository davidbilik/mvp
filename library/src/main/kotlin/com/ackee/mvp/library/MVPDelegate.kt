package com.ackee.mvp.library

import android.os.Bundle

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPDelegate<out P : Presenter<MVPView>>(val presenterCreator : PresenterCreator<P>) : PresenterView<P> {
    companion object {
        val TAG: String = MVPDelegate::class.java.name
        const val ID_KEY: String = "presenter_id"
    }

    private var arguments: Bundle? = null
    private var presenter: P? = null

    private var restoredState: Bundle? = null

    override fun saveState(state: Bundle) {

    }

    override fun restoreState(state: Bundle) {
        restoredState = state
    }


    override fun create(arguments: Bundle?) {
        this.arguments = arguments
    }

    override fun viewCreated(view: MVPView) {
        getPresenter().viewCreated(view)
    }

    override fun viewResumed(view: MVPView) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewPaused(view: MVPView) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun viewDestroyed(view: MVPView) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy(terminal: Boolean) {
        if (terminal) {
            //TODO remove prsenter from manager
        }
    }

    override fun getPresenter(): P {
        if (presenter == null && restoredState != null) {
            presenter = PresenterManager.get(restoredState!!.getString(ID_KEY, null)) as P
        }
        if (presenter == null) {
            presenter = presenterCreator.createPresenter()
            presenter?.create(if (restoredState == null) arguments!! else restoredState!!)
        }
        return presenter!!
    }


}