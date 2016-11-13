package com.ackee.mvp.library

import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * *
 * @since 13/11/16
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
annotation class PresenterClass(val value: KClass<out Presenter<*>>)