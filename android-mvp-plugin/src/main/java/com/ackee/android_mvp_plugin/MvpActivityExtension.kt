package com.ackee.android_mvp_plugin

import android.os.Bundle
import com.ackee.mvp.library.MvpView
import com.ackee.mvp.library.Presenter
import com.ackee.mvp.library.PresenterCreator
import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import com.trello.rxlifecycle2.navi.NaviLifecycle

/**
 * Navi extension to which all the MVP logic is delegated from activity.
 *
 * @param activity should implement the MVP view with the same type as [V] generic type as well as
 * [PresenterCreator] with [P] generic type and [NaviAppCompatActivity]
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/18/2017
 */
class MvpActivityExtension<V: MvpView, A, P : Presenter<V>>(activity: A) where A : PresenterCreator<P>, A : NaviAppCompatActivity, A : MvpView {

    lateinit var presenter: P

    init {
        val lifecycleProvider = NaviLifecycle.createActivityLifecycleProvider(activity)
        RxNavi.observe(activity, Event.CREATE)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter = activity.createPresenter(it ?: activity.intent.extras) }, { it.printStackTrace() })

        RxNavi.observe(activity, Event.SAVE_INSTANCE_STATE)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ /* TODO save presenter state to bundle */ }, { it.printStackTrace() })

        RxNavi.observe(activity, Event.RESUME)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter.attachView(activity as V) }, { it.printStackTrace() })

        RxNavi.observe(activity, Event.PAUSE)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter.detachView() }, { it.printStackTrace() })

        RxNavi.observe(activity, Event.DESTROY)
                .bindToLifecycle(lifecycleProvider)
                .subscribe({ presenter.onDestroy() }, { it.printStackTrace() })
    }
}