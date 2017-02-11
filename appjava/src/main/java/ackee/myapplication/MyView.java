package ackee.myapplication;

import com.ackee.mvp.core.PresenterClass;

/**
 * TODO add class description
 *
 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/
@PresenterClass(MyPresenter.class)
public class MyView implements IMyVIew{
    public static final String TAG = MyView.class.getName();
}
