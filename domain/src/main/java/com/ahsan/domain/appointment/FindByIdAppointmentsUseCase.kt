package com.ahsan.domain.appointment

import com.ahsan.data.models.AppointmentAndClient
import com.ahsan.data.repositories.AppointmentRepository
import javax.inject.Inject

class FindByIdAppointmentsUseCase @Inject constructor(
    private val repository: AppointmentRepository,
) {
    suspend operator fun invoke(id: Int): AppointmentAndClient{
        return repository.findById(id)
    }
}