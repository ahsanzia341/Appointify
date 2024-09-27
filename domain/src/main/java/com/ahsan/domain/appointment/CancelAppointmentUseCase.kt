package com.ahsan.domain.appointment

import com.ahsan.data.models.Appointment
import com.ahsan.data.repositories.AppointmentRepository
import javax.inject.Inject

class CancelAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository,
) {
    suspend operator fun invoke(appointment: Appointment){
        return repository.cancel(appointment)
    }
}