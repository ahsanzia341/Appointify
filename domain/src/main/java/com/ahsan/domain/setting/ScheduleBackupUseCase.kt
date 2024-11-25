package com.ahsan.domain.setting

import com.ahsan.data.repositories.SettingRepository
import javax.inject.Inject

class ScheduleBackupUseCase @Inject constructor(
    private val repository: SettingRepository,
) {
    suspend operator fun invoke(){
        return repository.scheduleBackup()
    }
}