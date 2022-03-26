package com.rentateam.rentateam_test.domain.observer

class Event<T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun getHasBeenHandled(): Boolean? {
        return hasBeenHandled
    }

    fun peekContent(): T? {
        return content
    }
}