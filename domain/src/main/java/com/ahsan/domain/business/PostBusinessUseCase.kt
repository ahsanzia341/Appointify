package com.ahsan.domain.business

import com.ahsan.data.models.Business
import com.ahsan.data.repositories.BusinessRepository
import javax.inject.Inject

class PostBusinessUseCase @Inject constructor(
    private val repository: BusinessRepository,
) {
    suspend operator fun invoke(business: Business){
        repository.create(business)
    }
}