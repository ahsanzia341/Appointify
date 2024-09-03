package com.ahsan.domain.appointment

import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.repositories.AppointmentRepository
import javax.inject.Inject

class GetAppointmentsUseCase @Inject constructor(
    private val repository: AppointmentRepository,
) {
    suspend operator fun invoke(): List<AppointmentAndClient>{
        return repository.getAll()
    }
}