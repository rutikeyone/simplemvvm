package com.ru.simplemvvm.foundation.model.coroutines

import com.ru.simplemvvm.foundation.model.ErrorResult
import com.ru.simplemvvm.foundation.model.FinalResult
import com.ru.simplemvvm.foundation.model.SuccessResult
import kotlinx.coroutines.CancellableContinuation
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun <T> CancellableContinuation<T>.toEmitter(): Emitter<T> {
    return object : Emitter<T> {
        var done = AtomicBoolean(false)

        override fun emit(finalResult: FinalResult<T>) {
            if (done.compareAndSet(false, true)) {
                when (finalResult) {
                    is SuccessResult -> resume(finalResult.data)
                    is ErrorResult -> resumeWithException(finalResult.exception)
                }
            }
        }

        override fun setCancelListener(cancelListener: CancelListener) {
            invokeOnCancellation { cancelListener() }
        }

    }
}