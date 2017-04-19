package com.ackee.mvp.library

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
 * Test presenter functionality.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/17/2017
 */
class PresenterTest {

    private lateinit var presenter: TestPresenter

    @get:Rule val rxRule = TrampolineRxSchedulerRule()

    @Before
    fun setup() {
        presenter = TestPresenter()
    }

    @Test
    fun no_view_initially() {
        val testObserver = presenter.viewSubject.test()
        assertEquals(0, testObserver.events[0].size)
    }

    @Test
    fun view_attachment_correct() {
        val view = mock<TestView>()
        presenter.attachView(view)
        val testObserver = presenter.viewSubject.test()
        assertEquals(1, testObserver.events[0].size)
        assertEquals(OptionalView(view), testObserver.events[0][0])
    }

    @Test
    fun view_detachment_correct() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        val testObserver = presenter.viewSubject.test()
        assertEquals(1, testObserver.events[0].size)
        assertEquals(OptionalView(null), testObserver.events[0][0])
    }

    @Test
    fun dispose_of_view_disposable_on_detach() {
        val disposable = Observable.just(Unit)
                .delay(1000, TimeUnit.MILLISECONDS)
                .test()
        presenter.bindToView(disposable)
        presenter.detachView()
        assertTrue(disposable.isDisposed)
    }

    @Test
    fun dispose_of_disposable_on_destroy() {
        val disposable = Observable.just(Unit)
                .delay(1000, TimeUnit.MILLISECONDS)
                .test()
        presenter.bind(disposable)
        presenter.onDestroy()
        assertTrue(disposable.isDisposed)
    }

    @Test
    fun on_view_ready_view_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.onViewReady { asserter = true }
        assertTrue(asserter)
    }

    @Test
    fun on_view_ready_view_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.onViewReady { asserter = true }
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun on_view_ready_view_detached() {
        val view = mock<TestView>()
        var asserter = false
        presenter.attachView(view)
        presenter.detachView()
        presenter.onViewReady { asserter = true }
        assertFalse(asserter)
    }

    @Test
    fun observable_deliver_to_view_onNext_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testObservable(Observable.just(Unit), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun observable_deliver_to_view_onNext_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testObservable(Observable.just(Unit), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun observable_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testObservable(Observable.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun observable_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testObservable(Observable.just(Unit), { asserter = true })
        assertFalse(asserter)
    }

    @Test
    fun single_deliver_to_view_onSuccess_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testSingle(Single.just(Unit), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun single_deliver_to_view_onSuccess_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testSingle(Single.just(Unit), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun single_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testSingle(Single.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun single_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testSingle(Single.just(Unit), { asserter = true })
        assertFalse(asserter)
    }

    @Test
    fun maybe_deliver_to_view_onSuccess_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testMaybe(Maybe.just(Unit), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun maybe_deliver_to_view_onSuccess_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testMaybe(Maybe.just(Unit), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun maybe_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testMaybe(Maybe.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun maybe_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testMaybe(Maybe.just(Unit), { asserter = true })
        assertFalse(asserter)
    }

    @Test
    fun completable_deliver_to_view_onComplete_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testCompletable(Completable.complete(), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun completable_deliver_to_view_onComplete_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testCompletable(Completable.complete(), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun completable_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testCompletable(Completable.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun completable_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testCompletable(Completable.complete(), { asserter = true })
        assertFalse(asserter)
    }

    private interface TestView : MvpView

    private class TestPresenter : Presenter<TestView>() {
        fun testObservable(testObservable: Observable<Unit>, onNext: TestView.(item: Unit) -> Unit,
                           onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testObservable.deliverToView(onNext, onError)
        }

        fun testSingle(testSingle: Single<Unit>, onSuccess: TestView.(item: Unit) -> Unit,
                       onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testSingle.deliverToView(onSuccess, onError)
        }

        fun testMaybe(testMaybe: Maybe<Unit>, onSuccess: TestView.(item: Unit) -> Unit,
                      onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testMaybe.deliverToView(onSuccess, onError)
        }

        fun testCompletable(testCompletable: Completable, onComplete: TestView.() -> Unit,
                            onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testCompletable.deliverToView(onComplete, onError)
        }
    }
}