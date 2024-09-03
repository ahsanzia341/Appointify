package com.ahsan.domain.appointment

import com.ahsan.data.models.Appointment
import com.ahsan.data.repositories.AppointmentRepository
import javax.inject.Inject

class UpdateAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository,
) {
    operator fun invoke(appointment: Appointment){
        return repository.update(appointment)
    }
}