package com.ru.simplemvvm.foundation.sideeffects.navigator

import com.ru.simplemvvm.foundation.views.BaseScreen

interface Navigator {
    fun launch(screen: BaseScreen)
    fun goBack(result: Any? = null)
}