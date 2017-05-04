package com.ackee.android_mvp_plugin

import android.os.Bundle
import android.os.Parcelable
import com.ackee.mvp.library.MvpView
import com.ackee.mvp.library.Presenter
import com.ackee.mvp.library.PresenterCreator
import com.ackee.mvp.library.ViewStatePersister
import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.component.support.NaviFragment
import com.trello.navi2.rx.RxNavi
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import com.trello.rxlifecycle2.navi.NaviLifecycle

/**
 * Navi extension to which all the MVP logic is delegated from activity.
 *
 * @param fragment should implement the MVP view with the same type as [V] generic type as well as
 * [PresenterCreator] with [P] generic type and [NaviFragment]
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/18/2017
 */
class MvpFragmentExtension<V : MvpView, F, P : Presenter<V, T>, T : Parcelable>(fragment: F) where F : PresenterCreator<P, T>, F : NaviFragment, F : MvpView {

    lateinit var presenter: P
    val statePersister = ViewStatePersister<T>()

    init {
        val lifecycleProvider = NaviLifecycle.createFragmentLifecycleProvider(fragment)
        RxNavi.observe(fragment, Event.CREATE)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter = fragment.createPresenter(statePersister.getFromBundle(it)) }, { it.printStackTrace() })

        RxNavi.observe(fragment, Event.SAVE_INSTANCE_STATE)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({
                    val savedState = presenter.stateToSave()
                    if (savedState != null) {
                        statePersister.saveToBundle(it, savedState)
                    }
                }, { it.printStackTrace() })

        RxNavi.observe(fragment, Event.RESUME)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter.attachView(fragment as V) }, { it.printStackTrace() })

        RxNavi.observe(fragment, Event.PAUSE)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter.detachView() }, { it.printStackTrace() })

        RxNavi.observe(fragment, Event.DESTROY)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter.onDestroy() }, { it.printStackTrace() })
    }
}