package com.ru.simplemvvm.foundation.sideeffects.permissions

import com.ru.simplemvvm.foundation.sideeffects.permissions.plugin.PermissionStatus

interface Permissions {
    fun hasPermissions(permission: String): Boolean
    suspend fun requestPermissions(permission: String): PermissionStatus
}