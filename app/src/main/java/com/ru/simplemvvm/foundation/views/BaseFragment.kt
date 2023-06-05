package com.ru.simplemvvm.foundation.views

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.ru.simplemvvm.foundation.model.ErrorResult
import com.ru.simplemvvm.foundation.model.PendingResult
import java.lang.Exception
import com.ru.simplemvvm.foundation.model.Result
import com.ru.simplemvvm.foundation.model.SuccessResult
import com.ru.simplemvvm.foundation.views.activity.ActivityDelegateHolder

abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int,
): Fragment(contentLayoutId) {

    abstract val viewModel: BaseViewModel

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
}