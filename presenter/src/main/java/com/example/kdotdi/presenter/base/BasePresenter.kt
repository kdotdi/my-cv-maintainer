package com.example.kdotdi.presenter.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V> : CoroutineScope, ViewModel() where V : BaseView {

    private var job: Job = SupervisorJob()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var view: V? = null

    var isFirstBind = true

    var latestViewChanges: Queue<V.() -> Unit> = LinkedList()

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    open fun onFirstBind() {
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    open fun onViewRestoreState() {
    }

    fun showSnackbarNoInternetConnection() {
        present { showSnackbarNoInternetConnection() }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun present(
        viewChange: (V.() -> Unit)?
    ) =
        view?.let {
            launch(Dispatchers.Main) {
                viewChange?.invoke(it)
            }
        } ?: run {
            latestViewChanges.add(viewChange)
        }

    fun bind(view: V) {
        if (this.view != null) {
            throw RuntimeException(
                "Concurrent view bind! Unexpected, second instance of view occurred before " +
                        "first one unbind."
            )
        }
        this.view = view
        if (isFirstBind) {
            isFirstBind = false
            onFirstBind()
        } else {
            onViewRestoreState()
        }
        while (!latestViewChanges.isEmpty()) {
            present(latestViewChanges.poll())
        }
    }

    open fun unbind() {
        this.view = null
    }

    override fun onCleared() {
        finish()
        super.onCleared()
    }

    open fun finish() {
        job.cancel()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
}
