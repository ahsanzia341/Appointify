package com.ahsan.domain.setting

import com.ahsan.data.repositories.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HasBackupUseCase @Inject constructor(
    private val repository: SettingRepository,
) {
    operator fun invoke(): Flow<Boolean?>{
        return repository.hasBackup()
    }
}