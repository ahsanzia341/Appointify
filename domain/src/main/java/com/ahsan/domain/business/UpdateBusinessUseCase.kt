package com.ahsan.domain.business

import com.ahsan.data.models.Business
import com.ahsan.data.repositories.BusinessRepository
import javax.inject.Inject

class UpdateBusinessUseCase @Inject constructor(
    private val repository: BusinessRepository,
) {
    suspend operator fun invoke(business: Business){
        repository.update(business)
    }
}