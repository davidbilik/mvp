package com.ackee.mvp

import android.os.Bundle
import com.ackee.mvp.base.MvpFragment

/**
 * Fragment example.
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 */
class MyView : MvpFragment<IMyView, MyPresenter>(), IMyView {
    override fun createPresenter(bundle: Bundle?): MyPresenter {
        return MyPresenter()
    }
}

