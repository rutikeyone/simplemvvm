package com.ru.simplemvvm.foundation.sideeffects.resources.plugin

import android.content.Context
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.resources.Resources

class ResourcesSideEffectMediator(
    private val context: Context,
): SideEffectMediator<Nothing>(), Resources {
    override fun getString(resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }
}