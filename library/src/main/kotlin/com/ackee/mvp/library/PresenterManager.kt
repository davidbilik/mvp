package com.ackee.mvp.library

import java.util.*

/**
 * The manager stores all the application presenters in ID-Presenter pairs. When the presenter
 * for any screen is created, it is added to the manager and may be accessed by the generated ID.
 * When the presenter is destroyed, it is removed from the manager.
 *
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 11/13/2016.
 */
object PresenterManager {
    var presenters : HashMap<String, Presenter<*>> = HashMap()

    fun add(presenter: Presenter<*>): String {
        val id: String = presenter.javaClass.name + System.nanoTime()
        presenters.put(id, presenter)
        return id
    }

    fun get(id: String) {
        presenters[id]
    }

    fun remove(id: String) {
        presenters.remove(id)
    }

    internal fun clear() {
        presenters.clear()
    }
}
