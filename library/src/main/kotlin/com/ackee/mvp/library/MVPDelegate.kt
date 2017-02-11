package com.ackee.mvp.library

import android.os.Bundle
import com.ackee.mvp.core.Presenter
import com.ackee.mvp.core.PresenterBinder
import com.ackee.mvp.core.PresenterManager

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
        getPresenter().viewResumed()
    }

    fun viewPaused() {
        getPresenter().viewPaused()
    }

    fun viewDestroyed() {
        getPresenter().viewDestroyed()
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
            presenter = findPresenterBinder(delegated)?.createPresenter() as Presenter<in Delegated>?
            presenter?.create()
        }
        return presenter!!
    }


    private fun findPresenterBinder(delegated: Delegated): PresenterBinder? {
        return Class.forName("${delegated.javaClass.simpleName}PresenterBinder").newInstance() as PresenterBinder
    }


}