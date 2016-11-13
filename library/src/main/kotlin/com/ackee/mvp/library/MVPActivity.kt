package com.ackee.mvp.library

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Implementation of Activity with MVP.

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPActivity<P : Presenter<Any, DataState>> : AppCompatActivity(), PresenterView<P> {
    companion object {
        val TAG: String = MVPFragment::class.java.name
    }

    val delegate: MVPDelegate<P> = MVPDelegate()

    override fun create(arguments: Bundle?) {
        delegate.create(arguments)
    }

    override fun viewCreated(view: Any) {
        delegate.viewCreated(view)
    }

    override fun viewResumed(view: Any) {
        delegate.viewResumed(view)
    }

    override fun viewPaused(view: Any) {
        delegate.viewPaused(view)
    }

    override fun viewDestroyed(view: Any) {
        delegate.viewDestroyed(view)
    }

    override fun destroy(terminal: Boolean) {
        delegate.destroy(terminal)
    }

    override fun getPresenter(): P = delegate.getPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create(intent.extras)
        viewCreated(this)
    }

    override fun onPause() {
        super.onPause()
        viewPaused(this)
    }

    override fun onResume() {
        super.onResume()
        viewResumed(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDestroyed(this)
        destroy(isFinishing)
    }

}