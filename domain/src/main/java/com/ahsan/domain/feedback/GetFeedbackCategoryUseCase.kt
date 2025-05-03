package com.ahsan.domain.feedback

import com.ahsan.data.models.FeedbackCategory
import com.ahsan.data.models.Service
import com.ahsan.data.repositories.FeedbackRepository
import com.ahsan.data.repositories.ServiceRepository
import javax.inject.Inject

class GetFeedbackCategoryUseCase @Inject constructor(
    private val repository: FeedbackRepository,
) {
    suspend operator fun invoke(): List<FeedbackCategory>{
        return repository.getCategories()
    }
}