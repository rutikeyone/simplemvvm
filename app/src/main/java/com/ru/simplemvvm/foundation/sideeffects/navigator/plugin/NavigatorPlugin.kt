package com.ru.simplemvvm.foundation.sideeffects.navigator.plugin

import android.content.Context
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPlugin
import com.ru.simplemvvm.foundation.sideeffects.navigator.Navigator

class NavigatorPlugin(
    private val navigator: Navigator,
) : SideEffectPlugin<NavigatorSideEffectMediator, Navigator> {

    override val mediatorClass: Class<NavigatorSideEffectMediator>
        get() = NavigatorSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Navigator> {
        return NavigatorSideEffectMediator()
    }

    override fun createImplementation(mediator: NavigatorSideEffectMediator): Navigator? {
        return navigator
    }
}