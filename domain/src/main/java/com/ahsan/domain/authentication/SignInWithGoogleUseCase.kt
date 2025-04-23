package com.ahsan.domain.authentication

import android.content.Context
import com.ahsan.data.AuthUiState
import com.ahsan.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(context: Context): Flow<AuthUiState?>{
        return repository.signInWithGoogle(context)
    }
}