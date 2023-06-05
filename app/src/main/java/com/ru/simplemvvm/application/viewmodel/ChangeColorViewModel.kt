package com.ru.simplemvvm.application.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.ru.simplemvvm.application.domain.repository.ColorRepository
import com.ru.simplemvvm.foundation.sideeffects.dialogs.Dialogs
import com.ru.simplemvvm.foundation.sideeffects.intents.Intents
import com.ru.simplemvvm.foundation.sideeffects.navigator.Navigator
import com.ru.simplemvvm.foundation.sideeffects.permissions.Permissions
import com.ru.simplemvvm.foundation.sideeffects.resources.Resources
import com.ru.simplemvvm.foundation.sideeffects.toasts.Toasts
import com.ru.simplemvvm.foundation.views.BaseViewModel



class ChangeColorViewModel(
    private val navigator: Navigator,
    private val permissions: Permissions,
    private val toasts: Toasts,
    private val dialogs: Dialogs,
    private val resources: Resources,
    private val intents: Intents,
    private val savedStateHandle: SavedStateHandle,
    private val colorRepository: ColorRepository,
) : BaseViewModel()