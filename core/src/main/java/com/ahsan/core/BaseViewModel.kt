package com.ahsan.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by Anwar Alhasan on 2/14/2024.
 */
abstract class BaseViewModel<ViewState, Event> : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState?>(null)
    val viewState = _viewState.asStateFlow()

    fun updateState(viewState: ViewState) {
        _viewState.value = viewState
    }
    abstract fun onTriggerEvent(event: Event)

}