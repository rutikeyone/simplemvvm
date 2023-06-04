package com.ru.simplemvvm.foundation.views

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.ru.simplemvvm.application.dependencies.viewmodel.HiltViewModelFactory
import com.ru.simplemvvm.foundation.model.ErrorResult
import com.ru.simplemvvm.foundation.model.PendingResult
import com.ru.simplemvvm.foundation.utils.MainThreadExecutor
import com.ru.simplemvvm.foundation.utils.ResourceActions
import java.lang.Exception
import java.util.concurrent.Executor
import com.ru.simplemvvm.foundation.model.Result
import com.ru.simplemvvm.foundation.model.SuccessResult
import com.ru.simplemvvm.foundation.views.activity.ActivityDelegateHolder

abstract class BaseFragment<T : BaseViewModel>(
    @LayoutRes contentLayoutId: Int,
): Fragment(contentLayoutId) {

    protected abstract val factory: HiltViewModelFactory
    protected abstract val viewModel: T?

    private val executor: Executor = MainThreadExecutor()
    protected val targetResource = ResourceActions<T>(executor)

    val target: ResourceActions<T>
        get() {
            if(viewModel != null && targetResource.resource == null) {
                this.targetResource.resource = viewModel
            }
            return targetResource
        }

    fun notifyScreenUpdates() {
        (requireActivity() as ActivityDelegateHolder).delegate.notifyScreenUpdates()
    }

    fun <T> renderResult(root: ViewGroup, result: Result<T>,
                         onPending: () -> Unit, onError: (e: Exception) -> Unit,
                         onSuccess: (T) -> Unit) {
        root.children.forEach { it.visibility = View.GONE }
        when(result) {
            is SuccessResult -> onSuccess(result.data)
            is ErrorResult -> onError(result.exception)
            is PendingResult -> onPending()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.target.clear()
    }
}