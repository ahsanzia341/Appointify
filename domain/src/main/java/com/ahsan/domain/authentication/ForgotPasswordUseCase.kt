package com.ahsan.domain.authentication

import com.ahsan.data.AuthUiState
import com.ahsan.data.models.Appointment
import com.ahsan.data.repositories.AppointmentRepository
import com.ahsan.data.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(email: String){
        return repository.resetPassword(email)
    }
}