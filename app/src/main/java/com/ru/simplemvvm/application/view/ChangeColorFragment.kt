package com.ru.simplemvvm.application.view

import android.os.Bundle
import android.util.Log
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.application.viewmodel.ChangeColorViewModel
import com.ru.simplemvvm.foundation.views.BaseFragment
import com.ru.simplemvvm.foundation.views.BaseScreen
import com.ru.simplemvvm.foundation.views.BaseViewModel
import com.ru.simplemvvm.foundation.views.screenViewModel


class ChangeColorFragment : BaseFragment(R.layout.fragment_change_color)  {
    data class Screen(private val namedColorModel: NamedColorModel): BaseScreen

    override val viewModel by screenViewModel<ChangeColorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", viewModel.toString())
    }


}
