package com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin

import com.ru.simplemvvm.foundation.model.ErrorResult
import com.ru.simplemvvm.foundation.model.coroutines.Emitter
import com.ru.simplemvvm.foundation.model.coroutines.toEmitter
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.dialogs.Dialogs
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.IllegalStateException

class DialogSideEffectMediator: SideEffectMediator<DialogSideEffectImpl>(), Dialogs {
    var retainedState = RetainedState()

    override suspend fun show(dialogConfig: DialogConfig): Boolean = suspendCancellableCoroutine { continuation ->
        val emitter = continuation.toEmitter()
        if(retainedState.record != null) {
            emitter.emit(ErrorResult(IllegalStateException("Can't launch more than 1 dialog at a time")))
            return@suspendCancellableCoroutine
        }
        val wrappedEmitter = Emitter.wrap(emitter) {
            retainedState.record = null
        }
        val record = DialogRecord(wrappedEmitter, dialogConfig)

    }

    class DialogRecord(
        val emitter: Emitter<Boolean>,
        val config: DialogConfig
    )

    class RetainedState(
        var record: DialogRecord? = null
    )

}