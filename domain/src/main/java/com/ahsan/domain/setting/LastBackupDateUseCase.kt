package com.ahsan.domain.setting

import com.ahsan.data.repositories.SettingRepository
import javax.inject.Inject

class LastBackupDateUseCase @Inject constructor(
    private val repository: SettingRepository,
) {
    suspend operator fun invoke(): Long? {
        return repository.getLastBackupDate()
    }
}