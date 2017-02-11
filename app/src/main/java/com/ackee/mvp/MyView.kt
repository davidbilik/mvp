package com.ackee.mvp

import android.os.Bundle
import com.ackee.mvp.core.PresenterClass
import com.ackee.mvp.library.MVPActivity
import getMyPresenter


/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * *
 * @since 14/11/16
 */
@PresenterClass(MyPresenter::class)
class MyView : MVPActivity(), IMyView {
    companion object {
        val TAG = MyView::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMyPresenter().loadData()
    }
}

