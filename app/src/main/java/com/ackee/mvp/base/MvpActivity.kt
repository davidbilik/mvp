package com.ackee.mvp.base

import android.os.Parcelable
import com.ackee.android_mvp_plugin.MvpActivityExtension
import com.ackee.mvp.library.MvpView
import com.ackee.mvp.library.Presenter
import com.ackee.mvp.library.PresenterCreator
import com.trello.navi2.component.support.NaviAppCompatActivity

/**
 * Base MVP activity example. It may extend any other custom activity.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/16/2017
 */
abstract class MvpActivity<V : MvpView, P : Presenter<V, T>, T : Parcelable> : NaviAppCompatActivity(), PresenterCreator<P, T>, MvpView {
    val mvpExtension = MvpActivityExtension(this)

    val presenter: P
        get() = mvpExtension.presenter
}