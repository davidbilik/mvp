
/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 11/13/2016.
 */
public class Delegate<P extends Presenter> implements Interface<P> {
    public static final String TAG = Delegate.class.getName();

    P presenter;

    void create(Object v) {
        presenter.onCreated(v);
    }
}
