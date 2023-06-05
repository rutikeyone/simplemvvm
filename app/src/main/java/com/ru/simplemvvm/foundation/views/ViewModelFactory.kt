package com.ru.simplemvvm.foundation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.ru.simplemvvm.foundation.SingletonScopeDependencies
import com.ru.simplemvvm.foundation.views.BaseScreen.Companion.ARG_SCREEN
import com.ru.simplemvvm.foundation.views.activity.ActivityDelegateHolder
import java.lang.reflect.Constructor


inline fun <reified VM : ViewModel> BaseFragment.screenViewModel() = viewModels<VM> {
    val application = requireActivity().application
    val screen = requireArguments().getSerializable(ARG_SCREEN) as BaseScreen
    val activityScopeViewModel = (requireActivity() as ActivityDelegateHolder).delegate.getActivityScopeViewModel()
    val dependencies = listOf(screen) + activityScopeViewModel.sideEffectMediators +
            SingletonScopeDependencies.getSingletonScopeDependencies(application)
    ViewModelFactory(dependencies, this)
}

class ViewModelFactory(
    private val dependencies: List<Any>,
    owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val constructors = modelClass.constructors
        val constructor = constructors.maxByOrNull { it.typeParameters.size }!!
        val dependenciesWithSavedState = dependencies + handle
        val arguments = findDependencies(constructor, dependenciesWithSavedState)
        return constructor.newInstance(*arguments.toTypedArray()) as T
    }

    private fun findDependencies(constructor: Constructor<*>, dependencies: List<Any>): List<Any> {
        val args = mutableListOf<Any>()
        constructor.parameterTypes.forEach { parameterClass ->
            val dependency = dependencies.first { parameterClass.isAssignableFrom(it.javaClass) }
            args.add(dependency)
        }
        return args
    }

}