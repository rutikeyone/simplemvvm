package com.ru.simplemvvm.foundation.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ru.simplemvvm.foundation.ActivityScopeViewModel
import com.ru.simplemvvm.foundation.sideeffects.SideEffectImplementationsHolder
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPlugin
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPluginManager
import com.ru.simplemvvm.foundation.utils.viewModelCreator

class ActivityDelegate(
    private val activity: AppCompatActivity,
) : LifecycleObserver {
    internal val sideEffectManager = SideEffectPluginManager()

    private val activityViewModel by activity.viewModelCreator<ActivityScopeViewModel> { ActivityScopeViewModel() }

    private val implementersHolder = SideEffectImplementationsHolder()

    init {
        activity.lifecycle.addObserver(this)
    }

    fun onBackPressed(): Boolean {
        return implementersHolder.implementations.any { it.onBackPressed() }
    }

    fun onSupportNavigateUp(): Boolean? {
        for(service in implementersHolder.implementations) {
            val value = service.onSupportNavigateUp()
            if(value != null) return value
        }
        return null
    }

    fun onCreate(savedInstanceState: Bundle?) {
        sideEffectManager.plugins.forEach {
            setupSideEffectMediator(it)
            setupSideEffectImplementer(it)
        }
        implementersHolder.implementations.forEach { it.onCreate(savedInstanceState) }
    }

    fun onSavedInstanceState(outState: Bundle) {
        implementersHolder.implementations.forEach { it.onSaveInstanceState(outState) }
    }

    fun onActivityResult(requestCode: Int, responseCode: Int, data: Intent?) {
        implementersHolder.implementations.forEach { it.onActivityResult(requestCode, responseCode, data) }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, granted: IntArray) {
        implementersHolder.implementations.forEach { it.onRequestPermissionsResult(requestCode, permissions, granted) }
    }

    fun notifyScreenUpdates() {
        implementersHolder.implementations.forEach { it.onRequestUpdates() }
    }

    fun getActivityScopeViewModel(): ActivityScopeViewModel {
        return activityViewModel
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        sideEffectManager.plugins.forEach {
            activityViewModel.sideEffectMediatorsHolder.setTargetWithPlugin(it, implementersHolder)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        activityViewModel.sideEffectMediatorsHolder.removeTargets()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        implementersHolder.clear()
        activity.lifecycle.removeObserver(this)
    }

    private fun setupSideEffectMediator(plugin: SideEffectPlugin<*, *>) {
        val holder = activityViewModel.sideEffectMediatorsHolder

        if(!holder.contains(plugin.mediatorClass)){
            holder.putWithPlugin(activity.applicationContext, plugin)
        }
    }

    private fun setupSideEffectImplementer(plugin: SideEffectPlugin<*, *>) {
        implementersHolder.putWithPlugin(plugin, activityViewModel.sideEffectMediatorsHolder, activity)
    }
}