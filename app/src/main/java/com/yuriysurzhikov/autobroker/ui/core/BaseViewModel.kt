package com.yuriysurzhikov.autobroker.ui.core

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val isEmpty = ObservableBoolean(false)
    val isLoading = ObservableBoolean(false)

}