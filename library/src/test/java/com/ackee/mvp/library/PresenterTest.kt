package com.ackee.mvp.library

import android.os.Parcel
import android.os.Parcelable
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.*
import io.reactivex.subjects.PublishSubject
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
        presenter.testObservableSticky(Observable.just(Unit), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun observable_deliver_to_view_onNext_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testObservableSticky(Observable.just(Unit), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun observable_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testObservableSticky(Observable.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun observable_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testObservableSticky(Observable.just(Unit), { asserter = true })
        assertFalse(asserter)
    }

    @Test
    fun single_deliver_to_view_onSuccess_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testSingleSticky(Single.just(Unit), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun single_deliver_to_view_onSuccess_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testSingleSticky(Single.just(Unit), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun single_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testSingleSticky(Single.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun single_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testSingleSticky(Single.just(Unit), { asserter = true })
        assertFalse(asserter)
    }

    @Test
    fun maybe_deliver_to_view_onSuccess_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testMaybeSticky(Maybe.just(Unit), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun maybe_deliver_to_view_onSuccess_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testMaybeSticky(Maybe.just(Unit), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun maybe_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testMaybeSticky(Maybe.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun maybe_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testMaybeSticky(Maybe.just(Unit), { asserter = true })
        assertFalse(asserter)
    }

    @Test
    fun completable_deliver_to_view_onComplete_attached_before_call() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testCompletableSticky(Completable.complete(), { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun completable_deliver_to_view_onComplete_attached_after_call() {
        val view = mock<TestView>()
        var asserter = false
        presenter.testCompletableSticky(Completable.complete(), { asserter = true })
        presenter.attachView(view)
        assertTrue(asserter)
    }

    @Test
    fun completable_deliver_to_view_onError() {
        val view = mock<TestView>()
        presenter.attachView(view)
        var asserter = false
        presenter.testCompletableSticky(Completable.fromCallable { throw Exception() }, { }, { asserter = true })
        assertTrue(asserter)
    }

    @Test
    fun completable_deliver_to_view_detached() {
        val view = mock<TestView>()
        presenter.attachView(view)
        presenter.detachView()
        var asserter = false
        presenter.testCompletableSticky(Completable.complete(), { asserter = true })
        assertFalse(asserter)
    }


    @Test
    fun deliver_again_after_reattach() {
        val view = mock<TestView>()
        var counter = 0
        presenter.testObservableSticky(Observable.just(Unit), { counter += 1 })
        presenter.attachView(view)
        assertEquals(1, counter)
        presenter.detachView()
        presenter.attachView(view)
        assertEquals(2, counter)
    }

    @Test
    fun deliver_new_value_to_view() {
        val view = mock<TestView>()
        var counter = 0
        val testSubject = PublishSubject.create<Int>()
        presenter.testObservableSticky(testSubject, { counter += 1 })
        presenter.attachView(view)
        testSubject.onNext(0)
        assertEquals(1, counter)
        testSubject.onNext(1)
        assertEquals(2, counter)
    }

    @Test
    fun not_deliver_again_when_reattach_deliverToView() {
        val view = mock<TestView>()
        var counter = 0
        presenter.testObservableNotSticky(Observable.just(Unit), { counter += 1 })
        presenter.attachView(view)
        presenter.detachView()
        presenter.attachView(view)
        assertEquals(1, counter)
    }


    @Test
    fun deliver_again_when_new_value_deliverToView() {
        val view = mock<TestView>()
        var counter = 0
        val testSubject = PublishSubject.create<Int>()
        presenter.testObservableNotSticky(testSubject, { counter += 1 })
        presenter.attachView(view)
        testSubject.onNext(1)
        assertEquals(1, counter)
        testSubject.onNext(1)
        assertEquals(2, counter)
    }


    @Test
    fun deliver_when_value_emitted_before_attach_deliverToView() {
        val view = mock<TestView>()
        var counter = 0
        val testSubject = PublishSubject.create<Int>()
        presenter.testObservableNotSticky(testSubject, { counter += 1 })
        testSubject.onNext(1)
        presenter.attachView(view)
        assertEquals(1, counter)
    }

    @Test
    fun not_deliver_when_view_detached_deliverToView() {
        val view = mock<TestView>()
        var counter = 0
        val testSubject = PublishSubject.create<Int>()
        presenter.testObservableNotSticky(testSubject, { counter += 1 })
        presenter.attachView(view)
        testSubject.onNext(1)
        assertEquals(1, counter)
        presenter.detachView()
        testSubject.onNext(2)
        assertEquals(1, counter)
    }

    @Test
    fun should_perform_operation_everytime_view_is_attached() {
        val view = mock<TestView>()
        var counter = 0
        presenter.testOnViewReadySticky { counter++ }
        assertEquals(0, counter)
        presenter.attachView(view)
        assertEquals(1, counter)
        presenter.detachView()
        presenter.attachView(view)
        assertEquals(2, counter)
    }

    @Test
    fun should_bind_to_observable_everytime_view_is_attached_and_unbind_when_view_is_detached() {
        val clickSubject = PublishSubject.create<Unit>()
        var disposeCounter = 0
        var subscribeCounter = 0
        val view = object : ObservableView {
            override fun buttonClicks() = clickSubject
                    .doOnSubscribe {
                        subscribeCounter++
                    }
                    .doOnDispose {
                        disposeCounter++
                    }
        }
        val presenter = ObservablePresenter()
        var actionCounter = 0
        presenter.testOnViewReadyStickyWithBindToView {
            actionCounter++
        }

        presenter.attachView(view)
        clickSubject.onNext(Unit)
        presenter.detachView()
        presenter.attachView(view)
        clickSubject.onNext(Unit)
        presenter.detachView()

        assertEquals(2, disposeCounter)
        assertEquals(2, subscribeCounter)
        assertEquals(2, actionCounter)
    }

    private interface TestView : MvpView

    private class TestPresenter : Presenter<TestView, TestState>() {
        fun testObservableSticky(testObservable: Observable<*>, onNext: TestView.(item: Any) -> Unit,
                onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testObservable.deliverToViewSticky(onNext, onError)
        }

        fun testSingleSticky(testSingle: Single<Unit>, onSuccess: TestView.(item: Unit) -> Unit,
                onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testSingle.deliverToViewSticky(onSuccess, onError)
        }

        fun testMaybeSticky(testMaybe: Maybe<Unit>, onSuccess: TestView.(item: Unit) -> Unit,
                onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testMaybe.deliverToViewSticky(onSuccess, onError)
        }

        fun testCompletableSticky(testCompletable: Completable, onComplete: TestView.() -> Unit,
                onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testCompletable.deliverToViewSticky(onComplete, onError)
        }

        fun testObservableNotSticky(testObservable: Observable<*>, onNext: TestView.(item: Any) -> Unit,
                onError: (TestView.(error: Throwable) -> Unit)? = null) {
            testObservable.deliverToView(onNext, onError)
        }

        fun testOnViewReadySticky(onNext: TestView.() -> Unit) {
            onViewReadySticky { onNext() }
        }
    }


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

    private interface ObservableView : MvpView {
        fun buttonClicks(): Observable<Unit>
    }

    private class ObservablePresenter : Presenter<ObservableView, TestState>() {
        fun testOnViewReadyStickyWithBindToView(action: () -> Unit) {
            onViewReadySticky {
                bindToView(
                        buttonClicks()
                                .subscribe({
                                    action()
                                })
                )
            }
        }
    }
}