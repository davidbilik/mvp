package com.ackee.mvp.library

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class MVPDelegateTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        val f: ConcreteFragment = ConcreteFragment()

        val delegate = MVPDelegate(f)

        val presenter = delegate.getPresenter()
    }
}