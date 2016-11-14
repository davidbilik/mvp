package com.ackee.mvp.library

import kotlin.reflect.KClass

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 13/11/16
 **/
interface PresenterCreator<out P : Presenter<*>> {
    fun createPresenter(): P
}

class ReflectivePresenterCreator<out P : Presenter<*>>(val clz: KClass<out P>?) : PresenterCreator<P> {
    override fun createPresenter(): P {
        return clz?.objectInstance!!
    }

    companion object {
        fun <P : Any> getClassFromAnnotation(clz: KClass<P>): KClass<out Presenter<*>>? {
            return null
            //TODO
//            for (a: Annotation in clz.annotations) {
//
//            }
//            val annotation: PresenterClass? = clz.annotations(PresenterClass::class.java)
//            if (annotation != null) {
//                return annotation.value
//            } else {
//                return null
//            }
        }
    }
}

fun <V, P : Presenter<V>> createPresenter(clz: KClass<out Presenter<out MVPView>>): P {
    return clz.java.newInstance() as P
}

fun getClassFromAnnotation(clz: KClass<out Any>): KClass<out Presenter<out MVPView>> {
    val annotation: PresenterClass? = clz.java.getAnnotation(PresenterClass::class.java)
    return annotation!!.value
}