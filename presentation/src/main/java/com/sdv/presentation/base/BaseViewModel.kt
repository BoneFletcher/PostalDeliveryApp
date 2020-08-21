package com.sdv.presentation.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    val loadingStateLiveData = MutableLiveData<Boolean>()

    protected fun showLoading(show: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            loadingStateLiveData.value = show
        }
    }

    protected fun launchAsync(
        shouldShowLoading: Boolean = true,
        onError: ((Exception) -> Unit)? = null,
        finally: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            if (shouldShowLoading) showLoading(true)
            try {
                block.invoke(this)
            } catch (e: CancellationException) {

            } catch (e: Exception) {
                Log.e("Error", "while launching async", e)
                onError?.invoke(e)
            } finally {
                if (shouldShowLoading) showLoading(false)
                finally?.invoke()
            }
        }
    }

    protected suspend fun launch(
        shouldShowLoading: Boolean = true,
        onError: ((Exception) -> Unit)? = null,
        finally: (() -> Unit)? = null,
        block: suspend () -> Unit
    ) {
        if (shouldShowLoading) showLoading(true)
        try {
            block.invoke()
        } catch (e: CancellationException) {

        } catch (e: Exception) {
            Log.e("Error", "while launching async", e)
            onError?.invoke(e)
        } finally {
            if (shouldShowLoading) showLoading(false)
            finally?.invoke()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}