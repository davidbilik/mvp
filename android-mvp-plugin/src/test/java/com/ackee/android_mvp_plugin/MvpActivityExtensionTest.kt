package com.ackee.android_mvp_plugin

import android.content.Context
import android.os.Bundle
import android.view.Window
import com.ackee.mvp.library.MvpView
import com.ackee.mvp.library.Presenter
import com.ackee.mvp.library.PresenterCreator
import com.trello.navi2.component.support.NaviAppCompatActivity

import org.junit.Test

import org.mockito.Mockito

/**
 * Test if the MVP extension works properly with test activity and presenter

 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * *
 * @since 4/18/2017
 */
class MvpActivityExtensionTest {


    @Test
    fun onCreate_called() {
        val mock = Mockito.spy(TestActivity::class.java)
        Mockito.`when`(mock.window).thenReturn(Mockito.mock(Window::class.java))
        val extension = MvpActivityExtension <TestView, TestActivity, TestPresenter>(mock)
        mock.testCreate()
    }

}

open class TestActivity : NaviAppCompatActivity(), PresenterCreator<TestPresenter>, MvpView {
    override fun createPresenter(bundle: Bundle?): TestPresenter {
        return TestPresenter()
    }

    fun testCreate() {
        super.onCreate(null)
    }

}

open class TestPresenter : Presenter<TestView>()

interface TestView : MvpView