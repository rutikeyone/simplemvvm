package com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ru.simplemvvm.foundation.model.SuccessResult
import com.ru.simplemvvm.foundation.sideeffects.SideEffectImplementation
import com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin.DialogSideEffectMediator.RetainedState
import com.ru.simplemvvm.foundation.sideeffects.dialogs.plugin.DialogSideEffectMediator.DialogRecord

class DialogSideEffectImpl(
    private val retainedState: RetainedState,
): SideEffectImplementation(), LifecycleObserver {
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        val record = retainedState.record ?: return
        showDialog(record)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        requireActivity().lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        removeDialog()
    }

    fun showDialog(record: DialogRecord) {
        val config = record.config
        val emitter = record.emitter
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(config.title)
            .setMessage(config.message)
            .setCancelable(config.cancellable)
        if(config.positiveButton != null) {
            builder.setPositiveButton(config.positiveButton) {_, _, ->
                emitter.emit(SuccessResult(true))
                dialog = null
            }
        }

        if(config.negativeButton != null) {
            builder.setPositiveButton(config.negativeButton) {_, _, ->
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }

        if(config.cancellable) {
            builder.setPositiveButton(config.negativeButton) {_, _, ->
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }
        val dialog = builder.create()
        dialog.show()
        this.dialog = dialog
    }

    fun removeDialog() {
        dialog?.dismiss();
        dialog = null
    }
}