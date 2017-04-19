package com.ackee.mvp.base

import com.ackee.android_mvp_plugin.MvpFragmentExtension
import com.ackee.mvp.library.MvpView
import com.ackee.mvp.library.Presenter
import com.ackee.mvp.library.PresenterCreator
import com.trello.navi2.component.support.NaviFragment

/**
 * Base MVP fragment example. It may extend any other custom fragment.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/16/2017
 */
abstract class MvpFragment<V : MvpView, P : Presenter<V>> : NaviFragment(), PresenterCreator<P>, MvpView {
    val mvpExtension = MvpFragmentExtension(this)
}