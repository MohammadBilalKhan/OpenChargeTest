package com.bellall.openchargemap.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created on 16/03/22.
 */
abstract class BaseViewModel: ViewModel() {

    protected val isLoading = MutableLiveData<Boolean>()
    protected val displayError = MutableLiveData<String>()
    protected val networkError = MutableLiveData<Boolean>()

    fun getIsLoadingLiveData(): LiveData<Boolean> {
        return isLoading
    }

    fun getDisplayErrorLiveData(): LiveData<String> {
        return displayError
    }

    fun networkError(): LiveData<Boolean>{
        return networkError
    }
}