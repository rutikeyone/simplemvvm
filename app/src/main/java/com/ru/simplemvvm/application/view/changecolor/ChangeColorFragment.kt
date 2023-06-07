package com.ru.simplemvvm.application.view.changecolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.GridLayoutManager
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.viewmodel.changecolor.ChangeColorViewModel
import com.ru.simplemvvm.application.viewmodel.onTryAgain
import com.ru.simplemvvm.application.viewmodel.renderSimpleResult
import com.ru.simplemvvm.databinding.FragmentChangeColorBinding
import com.ru.simplemvvm.foundation.views.BaseFragment
import com.ru.simplemvvm.foundation.views.BaseScreen
import com.ru.simplemvvm.foundation.views.HasScreenTitle
import com.ru.simplemvvm.foundation.views.screenViewModel


class ChangeColorFragment : BaseFragment(R.layout.fragment_change_color), HasScreenTitle  {
    class Screen(
        val currentColorId: Long
    ) : BaseScreen

    override val viewModel by screenViewModel<ChangeColorViewModel>()

    override fun getScreenTitle(): String? = viewModel.screenTitle.value

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentChangeColorBinding.inflate(inflater, container, false)

        val adapter = ColorsAdapter(viewModel)
        setupLayoutManager(binding, adapter)

        binding.saveButton.setOnClickListener { viewModel.onSavedPressed() }
        binding.cancelButton.setOnClickListener { viewModel.onCancelPressed() }

        viewModel.viewState.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(binding.root, result) { viewState ->
                adapter.items = viewState.colorsList
                binding.saveButton.visibility = if (viewState.showSaveButton) View.VISIBLE else View.INVISIBLE
                binding.cancelButton.visibility = if (viewState.showCancelButton) View.VISIBLE else View.INVISIBLE
                binding.saveProgressBar.visibility = if (viewState.showSaveProgressBar) View.VISIBLE else View.GONE
            }
        }

        viewModel.screenTitle.observe(viewLifecycleOwner) {
            notifyScreenUpdates()
        }

        onTryAgain(binding.root) {
            viewModel.onTryAgain()
        }

        return binding.root
    }

    private fun setupLayoutManager(binding: FragmentChangeColorBinding, adapter: ColorsAdapter) {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = binding.root.width
                val itemWidth = resources.getDimensionPixelSize(R.dimen.dp_120)
                val columns = width / itemWidth
                binding.colorsRecyclerView.adapter = adapter
                binding.colorsRecyclerView.layoutManager = GridLayoutManager(requireContext(), columns)
            }
        }, )
    }
}
