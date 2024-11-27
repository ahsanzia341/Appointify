package com.ahsan.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AppointmentAndClient(@Embedded val appointment: Appointment,
                                @Relation(entity = Client::class, parentColumn = "clientId", entityColumn = "id")
                                val client: Client,
                                @Relation(entity = Service::class, parentColumn = "appointmentId", entityColumn = "serviceId",
                                    associateBy = Junction(AppointmentServiceCrossRef::class)
                                )
                                val services: List<Service>)