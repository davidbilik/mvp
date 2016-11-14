package com.ackee.mvp.library

import android.os.Bundle

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPDelegate<Delegated : Any>(var delegated: Delegated) {
    companion object {
        val TAG: String = MVPDelegate::class.java.name
        const val ID_KEY: String = "presenter_id"
    }

    private var arguments: Bundle? = null
    private var presenter: Presenter<in Delegated>? = null

    private var restoredState: Bundle? = null

    fun saveState(state: Bundle) {

    }

    fun restoreState(state: Bundle) {
        restoredState = state
    }


    fun create(arguments: Bundle?) {
        this.arguments = arguments
    }

    fun viewCreated() {
        getPresenter().viewCreated(delegated)
    }

    fun viewResumed() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun viewPaused() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun viewDestroyed() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun destroy(terminal: Boolean) {
        if (terminal) {
            //TODO remove prsenter from manager
        }
    }

    fun getPresenter(): Presenter<in Delegated> {
        if (presenter == null && restoredState != null) {
            presenter = PresenterManager.get(restoredState!!.getString(ID_KEY, null)) as Presenter<in Delegated>
        }
        if (presenter == null) {
            presenter = createPresenter<Delegated, Presenter<Delegated>>(getClassFromAnnotation(delegated.javaClass.kotlin))
            presenter?.create(if (restoredState == null) arguments else restoredState!!)
        }
        return presenter!!
    }


}