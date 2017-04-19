package com.ackee.mvp.library

internal data class OptionalView<out V: MvpView>(val view: V?)