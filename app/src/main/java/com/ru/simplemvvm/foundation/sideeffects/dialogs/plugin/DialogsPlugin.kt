package com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin

import android.content.Context
import com.ru.simplemvvm.foundation.sideeffects.SideEffectMediator
import com.ru.simplemvvm.foundation.sideeffects.SideEffectPlugin

class DialogsPlugin: SideEffectPlugin<DialogSideEffectMediator, DialogSideEffectImpl> {
    override val mediatorClass: Class<DialogSideEffectMediator>
        get() = DialogSideEffectMediator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<DialogSideEffectImpl> {
        return DialogSideEffectMediator()
    }

    override fun createImplementation(mediator: DialogSideEffectMediator): DialogSideEffectImpl? {
        return DialogSideEffectImpl(mediator.retainedState)
    }
}