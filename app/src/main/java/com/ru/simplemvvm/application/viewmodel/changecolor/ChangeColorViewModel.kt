package com.ru.simplemvvm.application.viewmodel.changecolor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.application.domain.repository.ColorRepository
import com.ru.simplemvvm.application.view.changecolor.ChangeColorFragment
import com.ru.simplemvvm.application.view.changecolor.ColorsAdapter
import com.ru.simplemvvm.application.view.changecolor.NamedColorListTile
import com.ru.simplemvvm.foundation.model.PendingResult
import com.ru.simplemvvm.foundation.model.SuccessResult
import com.ru.simplemvvm.foundation.sideeffects.dialogs.Dialogs
import com.ru.simplemvvm.foundation.sideeffects.intents.Intents
import com.ru.simplemvvm.foundation.sideeffects.navigator.Navigator
import com.ru.simplemvvm.foundation.sideeffects.permissions.Permissions
import com.ru.simplemvvm.foundation.sideeffects.resources.Resources
import com.ru.simplemvvm.foundation.sideeffects.toasts.Toasts
import com.ru.simplemvvm.foundation.views.BaseViewModel
import com.ru.simplemvvm.foundation.views.LiveResult
import com.ru.simplemvvm.foundation.views.MediatorLiveResult
import com.ru.simplemvvm.foundation.views.MutableLiveResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class ChangeColorViewModel(
    screen: ChangeColorFragment.Screen,
    private val navigator: Navigator,
    private val permissions: Permissions,
    private val toasts: Toasts,
    private val dialogs: Dialogs,
    private val resources: Resources,
    private val intents: Intents,
    private val colorRepository: ColorRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(), ColorsAdapter.Listener {

    private val _availableColors = MutableLiveResult<List<NamedColorModel>>(PendingResult())
    private val _currentColorId = savedStateHandle.getLiveData("currentColorId", screen.currentColorId)
    private val _saveInProgress = MutableLiveData(false)

    private val _viewState = MediatorLiveResult<ViewState>();
    val viewState: LiveResult<ViewState>
        get() = _viewState

    val screenTitle: LiveData<String> = viewState.map { result ->
        if(result is SuccessResult) {
            val currentColor = result.data.colorsList.first { it.selected }
            resources.getString(R.string.change_color_screen_title, currentColor.namedColor.name)
        } else {
            resources.getString(R.string.change_color_screen_title_simple)
        }
    }

    init {
        load()
        _viewState.addSource(_availableColors) { mergeSources() }
        _viewState.addSource(_currentColorId) { mergeSources() }
        _viewState.addSource(_saveInProgress) { mergeSources() }
    }

    fun onSavedPressed() = viewModelScope.launch {
        try {
            _saveInProgress.value = true
            val currentColorId = _currentColorId.value ?: return@launch
            val currentColor = colorRepository.getById(currentColorId)
            colorRepository.setCurrentColor(currentColor).collect()
            navigator.goBack(currentColor)
        } catch (e: Exception) {
            if(e !is CancellationException) toasts.toast(resources.getString(R.string.error_happened))
        } finally {
            _saveInProgress.value = false
        }
    }

    override fun onColorChosen(namedColor: NamedColorModel) {
        if(_saveInProgress.value == true) return;
        _currentColorId.value = namedColor.id
    }

    fun onCancelPressed() {
        navigator.goBack();
    }

    fun onTryAgain() {
        load()
    }

    private fun mergeSources() {
        val colors = _availableColors.value ?: return
        val currentColorId = _currentColorId.value ?: return
        val saveInProgress = _saveInProgress.value ?: return

        _viewState.value = colors.map { colorsList ->
            ViewState(
                colorsList = colorsList.map { NamedColorListTile(it, currentColorId == it.id) },
                showSaveButton = !saveInProgress,
                showCancelButton = !saveInProgress,
                showSaveProgressBar = saveInProgress
            )
        }
    }

    private fun load() {
        _availableColors.value = PendingResult()
        into(_availableColors) { colorRepository.getAvailableColors() }
    }

    data class ViewState(
        val colorsList: List<NamedColorListTile>,
        val showSaveButton: Boolean,
        val showCancelButton: Boolean,
        val showSaveProgressBar: Boolean,
    )
}