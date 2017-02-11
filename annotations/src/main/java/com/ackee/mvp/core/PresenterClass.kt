package com.ackee.mvp.core

import kotlin.reflect.KClass

/**
 * TODO
 * */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
annotation class PresenterClass(val value: KClass<out Presenter<out MVPView>>)