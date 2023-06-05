package com.ru.simplemvvm.foundation.sideeffects.toasts.plugin

import android.content.Context
import android.widget.Toast
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.toasts.Toasts
import com.ru.simplemvvm.foundation.utils.MainThreadExecutor

class ToastSideEffectMediator(
    private val context: Context,
) : SideEffectMediator<Nothing>(), Toasts{

    private val executor = MainThreadExecutor()

    override fun toast(message: String) {
        executor.execute {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }


}