package com.ru.simplemvvm.application.view

import androidx.fragment.app.Fragment
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.foundation.views.BaseScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeColorFragment : Fragment(R.layout.fragment_change_color)  {
    data class Screen(private val namedColorModel: NamedColorModel): BaseScreen
}
