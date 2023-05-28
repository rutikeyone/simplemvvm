package com.ru.simplemvvm.foundation.utils

import java.util.concurrent.Executor

typealias ResourceAction<T> = (T) -> Unit

class ResourceActions<T>(
    private val executor: Executor = MainThreadExecutor()
) {
    private val actions = mutableListOf<ResourceAction<T>>()

    var resource: T? = null
        set(newValue) {
            field = newValue
            if(newValue != null) {
                actions.forEach { action ->
                    executor.execute { action(newValue) }
                }
                actions.clear()
            }
        }

    operator fun invoke(action: ResourceAction<T>) {
        val resource = this.resource
        if(resource == null) actions += action
        if(resource != null){
            executor.execute { action(resource) }
        }
    }

    fun clear() {
        actions.clear()
    }
}