package com.ru.simplemvvm.application

import com.ru.simplemvvm.application.data.repository.InMemoryColorsRepository
import com.ru.simplemvvm.foundation.SingletonScopeDependencies
import com.ru.simplemvvm.foundation.model.coroutines.IoDispatcher
import com.ru.simplemvvm.foundation.model.coroutines.WorkerDispatchers

object Initializer {
    fun initDependencies() = SingletonScopeDependencies.init { _ ->
        val ioDispatcher = IoDispatcher()
        val workerDispatcher = WorkerDispatchers()
        return@init listOf(
            ioDispatcher,
            workerDispatcher,
            InMemoryColorsRepository(ioDispatcher),
        )
    }
}