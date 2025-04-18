package com.ahsan.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.TopBar

@Composable
fun BackupScreen(navController: NavController) {
    val viewModel = hiltViewModel<BackupViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    BackupUI() {
        navController.popBackStack()
    }
}

@Composable
fun BackupUI(onBackPress: () -> Unit){
    Scaffold(topBar = { TopBar(title = stringResource(id = R.string.backup_data), onClickNavIcon = { onBackPress() }) }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ThemeButton(text = stringResource(id = R.string.backup_data)) { }
        }
    }
}

@Composable
@Preview
fun Preview(){
    BackupUI(){}
}