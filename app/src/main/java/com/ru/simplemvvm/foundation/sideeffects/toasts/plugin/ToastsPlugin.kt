package com.ru.simplemvvm.foundation.sideeffects.toasts.plugin

import android.content.Context
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPlugin
import com.ru.simplemvvm.foundation.sideeffects.toasts.Toasts

class ToastsPlugin: SideEffectPlugin<Toasts, Nothing> {
    override val mediatorClass: Class<Toasts>
        get() = Toasts::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        return ToastSideEffectMediator(applicationContext)
    }
}