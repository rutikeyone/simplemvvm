package com.ru.simplemvvm.foundation.sideeffects

import com.ru.simplemvvm.foundation.utils.MainThreadExecutor
import com.ru.simplemvvm.foundation.utils.ResourceActions
import java.util.concurrent.Executor

open class SideEffectMediator<Implementation>(
    private val executor: Executor = MainThreadExecutor()
) {
    protected val target = ResourceActions<Implementation>(executor)

    fun setTarget(target: Implementation?) {
        this.target.resource = target
    }

    fun clear() {
        this.target.clear()
    }
}