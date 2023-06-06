package com.ru.simplemvvm.application.viewmodel.changecolor

import com.ru.simplemvvm.application.data.model.NamedColorModel

data class NamedColorListTile(
    val namedColor: NamedColorModel,
    val selected: Boolean,
)