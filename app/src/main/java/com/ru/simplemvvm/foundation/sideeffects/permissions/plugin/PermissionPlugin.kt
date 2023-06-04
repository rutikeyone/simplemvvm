package com.ru.simplemvvm.foundation.sideeffects.permissions.plugin

import android.content.Context
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPlugin

class PermissionPlugin: SideEffectPlugin<PermissionSideEffectMediator, PermissionsSideEffectImpl> {
    override val mediatorClass: Class<PermissionSideEffectMediator>
        get() = PermissionSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<PermissionsSideEffectImpl> {
        return PermissionSideEffectMediator(applicationContext)
    }

    override fun createImplementation(mediator: PermissionSideEffectMediator): PermissionsSideEffectImpl? {
        return PermissionsSideEffectImpl(mediator.retainedState)
    }
}