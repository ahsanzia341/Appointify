package com.ahsan.feedback

import com.ahsan.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(): BaseViewModel<ViewState, FeedbackEvent>(){
    override fun onTriggerEvent(event: FeedbackEvent) {

    }
}