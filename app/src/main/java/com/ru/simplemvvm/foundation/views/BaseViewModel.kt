package com.ru.simplemvvm.foundation.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ru.simplemvvm.foundation.utils.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext

typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>

typealias ResultFlow<T> = Flow<Result<T>>
typealias ResultMutableStateFlow<T> = MutableStateFlow<Result<T>>

open class BaseViewModel: ViewModel() {
    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler(::coroutineExceptionHandler)

    protected val viewModelScope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        clearScope()
        super.onCleared()
    }

    open fun onResult(result: Any) {}

    open fun onBackPressed(): Boolean {
        clearScope()
        return false
    }

    //TODO
    fun <T> into(liveResult: MutableLiveResult<T>, block: suspend () -> T) {}

    //TODO
    fun <T> into(stateFlow: MutableStateFlow<Result<T>>, block: suspend () -> T) {}

    //TODO
    fun <T> SavedStateHandle.getStateFlow(key: String, initialValue: T): MutableStateFlow<T> {
        throw Exception()
    }

    protected open fun coroutineExceptionHandler(coroutineContext: CoroutineContext, throwable: Throwable) {}

    private fun clearScope() = viewModelScope.cancel()
}