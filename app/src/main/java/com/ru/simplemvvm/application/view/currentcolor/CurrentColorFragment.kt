package com.ru.simplemvvm.application.view.currentcolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ru.simplemvvm.R
import com.ru.simplemvvm.foundation.views.screenViewModel
import com.ru.simplemvvm.application.viewmodel.currentcolor.CurrentColorViewModel
import com.ru.simplemvvm.application.viewmodel.onTryAgain
import com.ru.simplemvvm.application.viewmodel.renderSimpleResult
import com.ru.simplemvvm.databinding.FragmentCurrentColorBinding
import com.ru.simplemvvm.foundation.views.BaseFragment
import com.ru.simplemvvm.foundation.views.BaseScreen

class CurrentColorFragment : BaseFragment(R.layout.fragment_current_color) {
    class Screen : BaseScreen

    override val viewModel by screenViewModel<CurrentColorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)
        viewModel.currentColorState.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(
                root = binding.root,
                result = result,
                onSuccess = {color ->
                    binding.colorView.setBackgroundColor(color.value)
                }
            )

            binding.changeColorButton.setOnClickListener { viewModel.navigateToChangeColor() }

            binding.askPermissionsButton.setOnClickListener { viewModel.requestPermission() }

            onTryAgain(binding.root) {
                viewModel.tryAgain()
            }
        }
        return binding.root
    }

}