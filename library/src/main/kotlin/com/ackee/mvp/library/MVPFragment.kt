package com.ackee.mvp.library

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ackee.mvp.core.MVPView
import com.ackee.mvp.core.Presenter

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
open class MVPFragment: Fragment(), MVPView {
    companion object {
        val TAG: String = MVPFragment::class.java.name
    }

    val delegate: MVPDelegate<out MVPView> = MVPDelegate(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.create(arguments)
        if (savedInstanceState != null) {
            delegate.restoreState(savedInstanceState)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delegate.viewCreated()
    }

    override fun onResume() {
        super.onResume()
        delegate.viewResumed()
    }

    override fun onPause() {
        super.onPause()
        delegate.viewPaused()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        delegate.viewDestroyed()
    }

    override fun onDestroy() {
        super.onDestroy()
        var anyParentIsRemoving = false

        var parent = parentFragment

        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }
        delegate.destroy(isRemoving || anyParentIsRemoving || activity.isFinishing)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.saveState(outState)
    }

    fun getPresenter() : Presenter<out Any> = delegate.getPresenter() as Presenter<out Any>

}