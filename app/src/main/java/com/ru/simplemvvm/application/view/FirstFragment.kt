package com.ru.simplemvvm.application.view

import android.os.Bundle
import android.util.Log
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.dependencies.viewmodel.HiltViewModelFactory
import com.ru.simplemvvm.application.dependencies.viewmodel.screenViewModel
import com.ru.simplemvvm.application.viewmodel.first.FirstViewModel
import com.ru.simplemvvm.foundation.views.BaseFragment
import com.ru.simplemvvm.foundation.views.BaseScreen
import com.ru.simplemvvm.foundation.views.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : BaseFragment<FirstViewModel>(R.layout.fragment_first) {
    class Screen: BaseScreen

    @Inject
    override lateinit var factory: FirstViewModel.Factory
    override val viewModel: FirstViewModel?
        get() {
            if(this::factory.isInitialized  && _target.resource != null) return _target.resource!!;
            val viewModel by screenViewModel<FirstViewModel>(factory)
            return viewModel
        }

    override fun onCreate(savedInstanceState: Bundle?) = target {
        Log.e("TAG", it.toString());
        super.onCreate(savedInstanceState)
    }
}
