package com.ackee.android_mvp_plugin

import android.os.Bundle
import com.ackee.mvp.library.MvpView
import com.ackee.mvp.library.Presenter
import com.ackee.mvp.library.PresenterCreator
import com.nhaarman.mockito_kotlin.mock
import com.trello.navi2.component.support.NaviAppCompatActivity

import org.junit.Test

import org.junit.Assert.*

/**
 * Test if the MVP extension works properly with test activity and presenter

 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * *
 * @since 4/18/2017
 */
class MvpActivityExtensionTest {

    // TODO write tests for plugins

//    @Test
//    fun onCreate_called() {
//        val mockPresenter = mock<TestPresenter>()
//        val activity = TestActivity(mockPresenter)
//        activity.testCreate()
//        assertEquals(mockPresenter, activity.mvpExtension.presenter)
//    }
//
//    private class TestActivity(val mockPresenter: TestPresenter) : NaviAppCompatActivity(), PresenterCreator<TestPresenter>, TestView {
//        val mvpExtension = MvpActivityExtension(this)
//
//        override fun createPresenter(bundle: Bundle?): TestPresenter {
//            return TestPresenter()
//        }
//
//        fun testCreate() {
//            onCreate(null)
//        }
//    }
//
//    private class TestPresenter : Presenter<TestView>()
//
//    private interface TestView : MvpView
}