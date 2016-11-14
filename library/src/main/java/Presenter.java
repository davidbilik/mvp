import rx.subjects.BehaviorSubject;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 11/13/2016.
 */
public class Presenter<V> {
    public static final String TAG = Presenter.class.getName();

    BehaviorSubject<V> subject;

    public void onCreated(V v) {
        subject.onNext(v);
    }
}
