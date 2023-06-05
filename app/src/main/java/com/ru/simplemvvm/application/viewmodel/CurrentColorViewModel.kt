package com.ru.simplemvvm.application.viewmodel

import android.Manifest
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.application.domain.repository.ColorRepository
import com.ru.simplemvvm.application.view.ChangeColorFragment
import com.ru.simplemvvm.foundation.model.PendingResult
import com.ru.simplemvvm.foundation.model.SuccessResult
import com.ru.simplemvvm.foundation.model.takeSuccess
import com.ru.simplemvvm.foundation.sideeffects.dialogs.Dialogs
import com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin.DialogConfig
import com.ru.simplemvvm.foundation.sideeffects.intents.Intents
import com.ru.simplemvvm.foundation.sideeffects.navigator.Navigator
import com.ru.simplemvvm.foundation.sideeffects.permissions.Permissions
import com.ru.simplemvvm.foundation.sideeffects.permissions.plugin.PermissionStatus
import com.ru.simplemvvm.foundation.sideeffects.resources.Resources
import com.ru.simplemvvm.foundation.sideeffects.toasts.Toasts
import com.ru.simplemvvm.foundation.views.BaseViewModel
import com.ru.simplemvvm.foundation.views.LiveResult
import com.ru.simplemvvm.foundation.views.MutableLiveResult
import kotlinx.coroutines.launch

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val permissions: Permissions,
    private val toasts: Toasts,
    private val dialogs: Dialogs,
    private val resources: Resources,
    private val intents: Intents,
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
            val message = resources.getString(R.string.changed_color, result.name)
            toasts.toast(message)
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
            dialogs.show(createPermissionAlreadyGrantedDialog())
        } else {
            when(permissions.requestPermissions(permission)) {
                PermissionStatus.GRANTED -> {
                    toasts.toast(resources.getString(R.string.permissions_grated))
                }
                PermissionStatus.DENIED -> {
                    toasts.toast(resources.getString(R.string.permissions_denied))
                }
                PermissionStatus.DENIED_FOREVER -> {
                    if(dialogs.show(createAskForLaunchingAppSettingsDialog())) {
                        intents.openSettings()
                    }
                }
            }
        }
    }

    private fun load(){
        _currentColorState.postValue(PendingResult())
        into(_currentColorState) { colorRepository.getCurrentColor() }
    }

    private fun createPermissionAlreadyGrantedDialog() = DialogConfig(
        title = resources.getString(R.string.dialog_permissions_title),
        message = resources.getString(R.string.permissions_already_granted),
        positiveButton = resources.getString(R.string.action_ok),
    )

    private fun createAskForLaunchingAppSettingsDialog() = DialogConfig(
        title = resources.getString(R.string.dialog_permissions_title),
        message = resources.getString(R.string.open_app_settings_message),
        positiveButton = resources.getString(R.string.action_open),
        negativeButton = resources.getString(R.string.action_cancel),
    )
}