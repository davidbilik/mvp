package com.ackee.android_mvp_plugin

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
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
        val extension = MvpActivityExtension <TestView, TestActivity, TestPresenter, TestState>(mock)
        mock.testCreate()
    }

}

open class TestActivity : NaviAppCompatActivity(), PresenterCreator<TestPresenter, TestState>, MvpView {
    override fun createPresenter(state: TestState?): TestPresenter {
        return TestPresenter()
    }

    fun testCreate() {
        super.onCreate(null)
    }

}

open class TestPresenter : Presenter<TestView, TestState>()

interface TestView : MvpView


class TestState : Parcelable {
    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<TestState> {
            override fun newArray(size: Int): Array<TestState> {
                return arrayOf()
            }

            override fun createFromParcel(source: Parcel): TestState {
                return TestState()
            }
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    override fun describeContents(): Int = 0

}