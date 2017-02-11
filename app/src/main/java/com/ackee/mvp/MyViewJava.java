package com.ackee.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ackee.mvp.core.PresenterClass;
import com.ackee.mvp.library.MVPFragment;

/**
 * TODO add class description
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/
@PresenterClass(MyPresenter.class)
public class MyViewJava extends MVPFragment implements IMyView {
    public static final String TAG = MyViewJava.class.getName();

    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
