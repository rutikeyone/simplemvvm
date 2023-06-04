package com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin

data class DialogConfig(
    val title: String,
    val message: String,
    val positiveButton: String? = null,
    val negativeButton: String? = null,
    val cancellable: Boolean = true
)