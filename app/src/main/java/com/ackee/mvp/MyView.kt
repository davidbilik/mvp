package com.ackee.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ackee.mvp.base.MvpActivity
import com.ackee.mvp.base.MvpFragment

/**
 * Fragment example.
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 */
class MyView : MvpActivity<IMyView, MyPresenter>(), IMyView {
    override fun createPresenter(bundle: Bundle?): MyPresenter {
        return MyPresenter(bundle)
    }

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        setContentView(textView)

    }

    override fun showText(text: String) {
        textView.text = text
    }
}

