package com.ru.simplemvvm.foundation.views

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.ru.simplemvvm.application.dependencies.viewmodel.HiltViewModelFactory
import com.ru.simplemvvm.application.dependencies.viewmodel.screenViewModel
import com.ru.simplemvvm.foundation.utils.MainThreadExecutor
import com.ru.simplemvvm.foundation.utils.ResourceActions
import java.lang.Exception
import java.util.concurrent.Executor
import kotlin.reflect.KProperty

abstract class BaseFragment<T : BaseViewModel>(
    @LayoutRes contentLayoutId: Int,
): Fragment(contentLayoutId) {

    protected abstract val factory: HiltViewModelFactory
    protected abstract val viewModel: T?

    private val executor: Executor = MainThreadExecutor()
    protected val _target = ResourceActions<T>(executor)

    val target: ResourceActions<T>
        get() {
            if(viewModel != null && _target.resource == null) {
                this._target.resource = viewModel
            }
            return _target
        }

    //TODO
    fun notifyScreenUpdates() {}

    //TODO
    fun <T> renderResult(root: ViewGroup, result: Result<T>,
                         onPending: () -> Unit, onError: (e: Exception) -> Unit,
                         onSuccess: (T) -> Unit) {
    }

    override fun onDestroy() {
        super.onDestroy()
        this.target.clear()
    }
}