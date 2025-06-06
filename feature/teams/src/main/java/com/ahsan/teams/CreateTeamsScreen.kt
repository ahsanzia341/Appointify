package com.ahsan.teams

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.EmailTextField
import com.ahsan.composable.PhoneTextField
import com.ahsan.composable.R
import com.ahsan.composable.RequiredTextField
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.TopBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.data.models.Team

@Composable
fun CreateTeamsScreen(navController: NavController) {
    val viewModel = hiltViewModel<CreateTeamsViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    CreateTeamsUI(viewState?.isLoading == true, onSubmit = {
        viewModel.onTriggerEvent(CreateTeamsEvent.Submit(it))
    }) {
        navController.popBackStack()
    }
}

@Composable
fun CreateTeamsUI(isLoading: Boolean, onSubmit: (Team) -> Unit, onBackPress: () -> Unit){
    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.create_team_member), onClickNavIcon = {
            onBackPress()
        })
    }, modifier = Modifier.padding(8.dp)) { padding ->
        var team by remember { mutableStateOf(Team()) }
        Column(Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RequiredTextField(stringResource(R.string.name)) {
                team.name = it
            }
            EmailTextField {
                team.email = it
            }
            PhoneTextField {
                team.phone = it
            }
            ThemeButton(text = stringResource(R.string.submit), enabled = !isLoading) { onSubmit(team) }
        }
    }
}

@Composable
@Preview
fun Preview(){
    SmartAppointmentTheme {
        CreateTeamsUI(false, {}) {  }
    }
}