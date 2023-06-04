package com.ru.simplemvvm.foundation.sideeffects.dialogs

import com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin.DialogConfig

interface Dialogs {
    suspend fun show(dialogConfig: DialogConfig): Boolean
}