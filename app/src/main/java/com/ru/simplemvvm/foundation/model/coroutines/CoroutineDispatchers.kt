package com.ru.simplemvvm.foundation.model.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class IoDispatcher(
    val value: CoroutineDispatcher = Dispatchers.IO
)

class WorkerDispatchers(
    val value: CoroutineDispatcher = Dispatchers.Default
)