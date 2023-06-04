package com.ru.simplemvvm.application.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.dependencies.viewmodel.screenViewModel
import com.ru.simplemvvm.application.viewmodel.CurrentColorViewModel
import com.ru.simplemvvm.application.viewmodel.onTryAgain
import com.ru.simplemvvm.application.viewmodel.renderSimpleResult
import com.ru.simplemvvm.databinding.FragmentCurrentColorBinding
import com.ru.simplemvvm.foundation.views.BaseFragment
import com.ru.simplemvvm.foundation.views.BaseScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrentColorFragment : BaseFragment<CurrentColorViewModel>(R.layout.fragment_current_color) {
    class Screen : BaseScreen

    @Inject
    override lateinit var factory: CurrentColorViewModel.Factory
    override val viewModel: CurrentColorViewModel?
        get() {
            if(this::factory.isInitialized  && targetResource.resource != null) return targetResource.resource!!;
            val viewModel by screenViewModel<CurrentColorViewModel>(factory)
            return viewModel
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)
        target {viewModel ->
            viewModel.currentColorState.observe(viewLifecycleOwner) { result ->
                renderSimpleResult(
                    root = binding.root,
                    result = result,
                    onSuccess = {color ->
                        binding.colorView.setBackgroundColor(color.value)
                    }
                )
            }

            binding.changeColorButton.setOnClickListener { viewModel.navigateToChangeColor() }

            binding.askPermissionsButton.setOnClickListener { viewModel.requestPermission() }

            onTryAgain(binding.root) {
                viewModel.tryAgain()
            }

        }
        return binding.root
    }

}