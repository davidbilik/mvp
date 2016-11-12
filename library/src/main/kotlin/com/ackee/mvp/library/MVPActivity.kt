package com.ackee.mvp.library
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 12/11/16
 **/
class MVPActivity<P : Presenter<Any, DataState>>  : AppCompatActivity(), PresenterView{
    companion object {

        val TAG: String = MVPFragment::class.java.name
    }

    val delegate : MVPDelegate<P> = MVPDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create(intent.extras)
    }

    override fun create(bundle: Bundle?) {
        delegate.onCreate(bundle)
    }

}
