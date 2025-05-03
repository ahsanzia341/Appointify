package com.ahsan.domain.team

import com.ahsan.data.models.Team
import com.ahsan.data.repositories.SettingRepository
import com.ahsan.data.repositories.TeamRepository
import javax.inject.Inject

class PostTeamUseCase @Inject constructor(
    private val repository: TeamRepository,
) {
    suspend operator fun invoke(team: Team){
        return repository.post(team)
    }
}