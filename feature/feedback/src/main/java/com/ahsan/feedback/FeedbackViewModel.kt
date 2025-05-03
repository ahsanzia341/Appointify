package com.ahsan.feedback

import androidx.lifecycle.viewModelScope
import com.ahsan.core.BaseViewModel
import com.ahsan.data.models.Feedback
import com.ahsan.data.models.FeedbackCategory
import com.ahsan.domain.feedback.GetFeedbackCategoryUseCase
import com.ahsan.domain.feedback.PostFeedbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val feedbackUseCase: PostFeedbackUseCase,
    private val getFeedbackCategoryUseCase: GetFeedbackCategoryUseCase
): BaseViewModel<ViewState, FeedbackEvent>() {

    private var categories = listOf<FeedbackCategory>()
    override fun onTriggerEvent(event: FeedbackEvent) {
        when (event) {
            is FeedbackEvent.Submit -> post(event.feedback)
            FeedbackEvent.GetCategories -> getCategories()
        }
    }

    init {
        getCategories()
    }

    private fun post(feedback: Feedback) {
        updateState(ViewState(isLoading = true, feedbackCategories = categories))
        viewModelScope.launch {
            feedbackUseCase(feedback)
            updateState(ViewState(isLoading = false, feedbackCategories = categories))
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categories = getFeedbackCategoryUseCase()
            updateState(
                ViewState(
                    isLoading = false,
                    feedbackCategories = categories
                )
            )
        }
    }
}