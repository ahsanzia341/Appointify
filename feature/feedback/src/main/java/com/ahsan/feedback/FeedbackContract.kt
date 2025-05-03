package com.ahsan.feedback

import com.ahsan.data.models.Feedback
import com.ahsan.data.models.FeedbackCategory


data class ViewState(val isLoading: Boolean = false, val feedbackCategories: List<FeedbackCategory>)

sealed class FeedbackEvent{
    data class Submit(val feedback: Feedback): FeedbackEvent()
    data object GetCategories: FeedbackEvent()
}