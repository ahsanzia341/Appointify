package com.ahsan.domain.setting

import com.ahsan.data.repositories.SettingRepository
import javax.inject.Inject

class PostBackupUseCase @Inject constructor(
    private val repository: SettingRepository,
) {
    suspend operator fun invoke(){
        return repository.backupData()
    }
}