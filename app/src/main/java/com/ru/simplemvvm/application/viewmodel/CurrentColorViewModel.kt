package com.ru.simplemvvm.application.viewmodel

import android.Manifest
import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.application.dependencies.viewmodel.HiltViewModelFactory
import com.ru.simplemvvm.application.domain.repository.ColorRepository
import com.ru.simplemvvm.application.view.ChangeColorFragment
import com.ru.simplemvvm.foundation.model.PendingResult
import com.ru.simplemvvm.foundation.model.SuccessResult
import com.ru.simplemvvm.foundation.model.takeSuccess
import com.ru.simplemvvm.foundation.sideeffects.navigator.Navigator
import com.ru.simplemvvm.foundation.sideeffects.permissions.Permissions
import com.ru.simplemvvm.foundation.sideeffects.permissions.plugin.PermissionStatus
import com.ru.simplemvvm.foundation.views.BaseViewModel
import com.ru.simplemvvm.foundation.views.LiveResult
import com.ru.simplemvvm.foundation.views.MutableLiveResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CurrentColorViewModel @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    @Assisted private val permissions: Permissions,
    private val colorRepository: ColorRepository,
) : BaseViewModel() {

    private val _currentColorState = MutableLiveResult<NamedColorModel>(PendingResult());
    val currentColorState: LiveResult<NamedColorModel>
        get() = _currentColorState

    init {
        viewModelScope.launch {
            colorRepository.listenCurrentColor().collect {
                _currentColorState.postValue(SuccessResult(it))
            }
        }
        load()
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if(result is NamedColorModel) {

        }
    }

    fun navigateToChangeColor() {
        val currentColor = currentColorState.value.takeSuccess() ?: return
        val screen = ChangeColorFragment.Screen(currentColor)
        navigator.launch(screen)

    }

    fun tryAgain() {
        load()
    }

    fun requestPermission() = viewModelScope.launch {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val hasPermission = permissions.hasPermissions(permission)
        if(hasPermission) {

        } else {
            when(permissions.requestPermissions(permission)) {
                PermissionStatus.GRANTED -> {}
                PermissionStatus.DENIED -> {}
                PermissionStatus.DENIED_FOREVER -> {}
            }
        }
    }

    private fun load(){
        _currentColorState.postValue(PendingResult())
        into(_currentColorState) { colorRepository.getCurrentColor() }
    }

    @AssistedFactory
    interface Factory: HiltViewModelFactory {
        fun create(
            navigator: Navigator,
            permissions: Permissions,
        ): CurrentColorViewModel
    }

}