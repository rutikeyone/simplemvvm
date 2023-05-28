package com.ru.simplemvvm.foundation.sideeffects

class SideEffectPluginManager {
    private val _plugins = mutableListOf<SideEffectPlugin<*, *>>()

    internal val plugins: List<SideEffectPlugin<*, *>>
        get() = _plugins

    fun <Mediator, Implementation> register(plugin: SideEffectPlugin<Mediator, Implementation>) {
        _plugins.add(plugin)
    }
}