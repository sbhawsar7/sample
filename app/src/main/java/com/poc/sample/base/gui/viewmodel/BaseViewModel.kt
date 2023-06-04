package com.poc.sample.base.gui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.base.displayName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    init {
        Timber.d("init: $displayName")
    }

    protected val _failure: MutableLiveData<Exception> by lazy { MutableLiveData() }

    val failure: LiveData<Exception> get() = _failure

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared: $displayName")
    }

    protected fun launchDefaultCoroutine(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                _failure.postValue(e)
            }
        }
    }


    protected fun launchIOCoroutine(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                _failure.postValue(e)
            }
        }
    }}