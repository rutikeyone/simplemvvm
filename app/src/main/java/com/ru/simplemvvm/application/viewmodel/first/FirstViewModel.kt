package com.ru.simplemvvm.application.viewmodel.first

import androidx.lifecycle.ViewModel
import com.ru.simplemvvm.application.data.FirstRepository
import com.ru.simplemvvm.application.dependencies.viewmodel.HiltViewModelFactory
import com.ru.simplemvvm.foundation.sideeffects.navigator.Navigator
import com.ru.simplemvvm.foundation.views.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class FirstViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val firstRepository: FirstRepository
) : BaseViewModel() {

    override fun toString(): String {
        return "$navigator $firstRepository"
    }

    @AssistedFactory
    interface Factory : HiltViewModelFactory {
        fun create(navigator: Navigator): FirstViewModel
    }
}

