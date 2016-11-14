package com.ackee.mvp.library

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Implementation of Activity with MVP.

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MVPFragment::class.java.name
    }

    val delegate: MVPDelegate<out MVPActivity> = MVPDelegate(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.create(intent.extras)
        delegate.viewCreated()
    }

    override fun onPause() {
        super.onPause()
        delegate.viewPaused()
    }

    override fun onResume() {
        super.onResume()
        delegate.viewResumed()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.viewDestroyed()
        delegate.destroy(isFinishing)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        delegate.restoreState(savedInstanceState)
    }
}
