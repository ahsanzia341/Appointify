package com.ahsan.domain.feedback

import com.ahsan.data.models.Feedback
import com.ahsan.data.models.Service
import com.ahsan.data.repositories.FeedbackRepository
import com.ahsan.data.repositories.ServiceRepository
import javax.inject.Inject

class PostFeedbackUseCase @Inject constructor(
    private val repository: FeedbackRepository,
) {
    suspend operator fun invoke(feedback: Feedback){
        return repository.post(feedback)
    }
}