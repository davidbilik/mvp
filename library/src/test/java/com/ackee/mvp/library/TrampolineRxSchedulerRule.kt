package com.ackee.mvp.library

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


/**
 * Custom rule for Rx that allows to run all Rx chains on trampoline scheduler in tests.
 *
 * @author Georgiy Shur (georgiy.shur@ackee.cz)
 * @since 4/18/2017
 */
class TrampolineRxSchedulerRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
           override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}