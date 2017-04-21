package com.ackee.mvp

import android.os.Bundle
import android.util.Log
import com.ackee.mvp.library.Presenter
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Presenter example.
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/
class MyPresenter(state: Bundle?) : Presenter<IMyView>(state) {

    var value = 0L

    init {
        onViewReady {
            showText("Hello world")
        }
        val offset = state?.getLong("offset") ?: 0
        bind(Observable.interval(1, TimeUnit.SECONDS)
                .subscribe {
                    onViewReady {
                        value = it
                        showText("${offset + it}")
                    }
                }
        )
    }

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putLong("offset", value)
    }

}