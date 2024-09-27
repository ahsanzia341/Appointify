package com.ahsan.domain.authentication

import com.ahsan.data.AuthUiState
import com.ahsan.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(){
        repository.delete()
    }
}