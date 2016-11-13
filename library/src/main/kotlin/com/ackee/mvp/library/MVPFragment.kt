package com.ackee.mvp.library

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPFragment<P : Presenter<Any, DataState>> : Fragment(), PresenterView<P> {
    companion object {
        val TAG: String = MVPFragment::class.java.name
    }

    override fun destroy(terminal: Boolean) {
        delegate.destroy(terminal)
    }

    override fun getPresenter(): P = delegate.getPresenter()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create(arguments)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(this)
    }

    override fun onResume() {
        super.onResume()
        viewResumed(this)
    }

    override fun onPause() {
        super.onPause()
        viewPaused(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDestroyed(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        var anyParentIsRemoving = false

        var parent = parentFragment

        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }
        destroy(isRemoving || anyParentIsRemoving || activity.isFinishing)
    }

}