package com.ru.simplemvvm.application.dependencies.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.ru.simplemvvm.foundation.views.BaseScreen
import com.ru.simplemvvm.foundation.views.BaseScreen.Companion.ARG_SCREEN
import com.ru.simplemvvm.foundation.views.BaseViewModel
import com.ru.simplemvvm.foundation.views.activity.ActivityDelegateHolder


inline fun <reified VM: BaseViewModel> Fragment.screenViewModel(factory: HiltViewModelFactory) = viewModels<VM> {
    val application = requireActivity().application
    val screen = requireArguments().getSerializable(ARG_SCREEN) as BaseScreen
    val activityScopeViewModel = (requireActivity() as ActivityDelegateHolder).delegate.getActivityScopeViewModel()
    val dependencies = listOf(screen) + activityScopeViewModel.sideEffectMediators
    ViewModelFactory(dependencies, factory, this)
}


class ViewModelFactory(
    private val dependencies: List<Any>,
    private val factory: HiltViewModelFactory,
    owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
     override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val factoryClass = factory.javaClass
        val methods = factoryClass.methods.filter {  it.returnType == modelClass }
        val method = methods.maxByOrNull { it.typeParameters.size }!!
        val parameterTypes = method.parameterTypes
        val arguments = findDependencies(parameterTypes, dependencies)
        return method.invoke(factory, *arguments.toTypedArray()) as T;
    }

    private fun findDependencies(parameterTypes: Array<Class<*>>, dependencies: List<Any>): List<Any> {
        val args = mutableListOf<Any>()
        parameterTypes.forEach { parameterClass ->
            val dependency = dependencies.first { parameterClass.isAssignableFrom(it.javaClass) }
            args.add(dependency)
        }
        return args
    }
}