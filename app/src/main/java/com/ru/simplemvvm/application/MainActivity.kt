package com.ru.simplemvvm.application

import android.os.Bundle
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.view.FirstFragment
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPluginManager
import com.ru.simplemvvm.foundation.sideeffects.navigator.plugin.NavigatorPlugin
import com.ru.simplemvvm.foundation.sideeffects.navigator.plugin.StackFragmentNavigator
import com.ru.simplemvvm.foundation.views.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity() {
    override fun registerPlugins(manager: SideEffectPluginManager) = with (manager) {
        val navigator = createNavigator()
        register(NavigatorPlugin(navigator))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    private fun createNavigator() = StackFragmentNavigator(
        containerId = R.id.fragmentContainer,
        defaultTitle = getString(R.string.app_name),
        animations = StackFragmentNavigator.Animations(
            enterAnim = R.anim.enter,
            exitAnim = R.anim.exit,
            popEnterAnim = R.anim.pop_enter,
            popExitAnim = R.anim.pop_exit
        ),
        initialScreenCreator = { FirstFragment.Screen() }
    )
}