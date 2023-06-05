package com.ru.simplemvvm.foundation.sideeffects.intents.plugin

import android.content.Context
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPlugin

class IntentsPlugin: SideEffectPlugin<IntentsSideEffectMediator, Nothing> {
    override val mediatorClass: Class<IntentsSideEffectMediator>
        get() = IntentsSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        return IntentsSideEffectMediator(applicationContext);
    }
}