package com.ackee.mvp

import android.os.Parcel
import android.os.Parcelable
import com.ackee.mvp.library.Presenter


/**
 * Presenter example.
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/
class MyPresenter(state: MyState?) : Presenter<IMyView, MyState>() {


    init {
        onViewReady {
            showText(state?.text ?: "Hello world")
        }
    }

    override fun stateToSave(): MyState? = MyState("Stored save state")
}

data class MyState(val text: String) : Parcelable {
    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<MyState> {
            override fun newArray(size: Int): Array<MyState> {
                return arrayOf()
            }

            override fun createFromParcel(source: Parcel): MyState {
                return MyState(source.readString())
            }
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(text)
    }

    override fun describeContents(): Int = 0

}