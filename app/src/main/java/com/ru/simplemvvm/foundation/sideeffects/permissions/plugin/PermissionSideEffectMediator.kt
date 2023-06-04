package com.ru.simplemvvm.foundation.sideeffects.permissions.plugin

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.ru.simplemvvm.foundation.model.ErrorResult
import com.ru.simplemvvm.foundation.model.coroutines.Emitter
import com.ru.simplemvvm.foundation.model.coroutines.toEmitter
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.permissions.Permissions
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.IllegalStateException

class PermissionSideEffectMediator(
    private val context: Context,
): SideEffectMediator<PermissionsSideEffectImpl>(), Permissions {

    val retainedState = RetainedState()

    override fun hasPermissions(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    override suspend fun requestPermissions(permission: String): PermissionStatus = suspendCancellableCoroutine { continuation ->
        val emitter = continuation.toEmitter()
        if(retainedState.emitter != null) {
            emitter.emit(ErrorResult(IllegalStateException("Only one permission request can be active")))
            return@suspendCancellableCoroutine
        }
        retainedState.emitter = emitter
        target {implementation ->
            implementation.requestPermission(permission)
        }
    }
    class RetainedState(
        var emitter: Emitter<PermissionStatus>? = null
    )
}