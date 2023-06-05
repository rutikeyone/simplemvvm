package com.ru.simplemvvm.application

import android.os.Bundle
import androidx.compose.ui.window.Dialog
import com.ru.simplemvvm.R
import com.ru.simplemvvm.application.view.CurrentColorFragment
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPluginManager
import com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin.DialogsPlugin
import com.ru.simplemvvm.foundation.sideeffects.intents.plugin.IntentsPlugin
import com.ru.simplemvvm.foundation.sideeffects.navigator.plugin.NavigatorPlugin
import com.ru.simplemvvm.foundation.sideeffects.navigator.plugin.StackFragmentNavigator
import com.ru.simplemvvm.foundation.sideeffects.permissions.plugin.PermissionPlugin
import com.ru.simplemvvm.foundation.sideeffects.resources.plugin.ResourcesPlugin
import com.ru.simplemvvm.foundation.sideeffects.toasts.plugin.ToastsPlugin
import com.ru.simplemvvm.foundation.views.activity.BaseActivity

class MainActivity: BaseActivity() {
    override fun registerPlugins(manager: SideEffectPluginManager) = with (manager) {
        val navigator = createNavigator()
        register(NavigatorPlugin(navigator))
        register(PermissionPlugin())
        register(ToastsPlugin())
        register(DialogsPlugin())
        register(ResourcesPlugin())
        register(IntentsPlugin())
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
        initialScreenCreator = { CurrentColorFragment.Screen() }
    )
}