package com.ahsan.teams

import com.ahsan.data.models.Team

data class ViewState(val isLoading: Boolean = false)

sealed class CreateTeamsEvent{
    data class Submit(val team: Team): CreateTeamsEvent()
}