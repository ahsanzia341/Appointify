package com.ahsan.teams

import com.ahsan.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTeamsViewModel @Inject constructor(): BaseViewModel<ViewState, CreateTeamsEvent>() {
    override fun onTriggerEvent(event: CreateTeamsEvent) {

    }
}