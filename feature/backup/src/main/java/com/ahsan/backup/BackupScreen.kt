package com.ahsan.backup

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahsan.composable.R
import com.ahsan.composable.ThemeButton
import com.ahsan.composable.ThemeSwitch
import com.ahsan.composable.ThemeText
import com.ahsan.composable.TopBar
import com.ahsan.composable.theme.SmartAppointmentTheme
import com.ahsan.core.Constant
import com.ahsan.core.extension.toEasyFormat
import java.util.Date
import androidx.core.content.edit

@Composable
fun BackupScreen(navController: NavController) {
    val viewModel = hiltViewModel<BackupViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    BackupUI(viewState?.lastBackupDate, {
        if(it){
            viewModel.onTriggerEvent(BackupEvent.ScheduleBackup)
        }
        else{
            viewModel.onTriggerEvent(BackupEvent.CancelScheduleBackup)
        }
    }, {
        viewModel.onTriggerEvent(BackupEvent.BackupData)
    }) {
        navController.popBackStack()
    }
}

@Composable
fun BackupUI(lastBackupDate: Date?, onAutoBackupSwitched: (Boolean) -> Unit, onBackupDataPressed:() -> Unit, onBackPress: () -> Unit){
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences(Constant.SHARED_PREF_KEY, Context.MODE_PRIVATE)

    var backupState by remember {
        mutableStateOf(sharedPref.getBoolean(Constant.IS_AUTO_BACKUP, false))
    }
    Scaffold(topBar = { TopBar(title = stringResource(id = R.string.backup_data), onClickNavIcon = { onBackPress() }) }, modifier = Modifier.padding(8.dp)) { padding ->
        Column(modifier = Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ThemeText(text = "Use this feature to share your data across multiple devices and teams.")
            ThemeText(text = "Last backup date: ${lastBackupDate?.toEasyFormat() ?: "Never"}")
            ThemeButton(text = stringResource(id = R.string.backup_data)) {
                onBackupDataPressed()
            }
            ThemeSwitch(stringResource(R.string.automatic_daily_backup)) {
                backupState = it
                sharedPref.edit { putBoolean(Constant.IS_AUTO_BACKUP, it) }
                onAutoBackupSwitched(it)
            }
        }
    }
}

@Composable
@Preview
fun Preview(){
    SmartAppointmentTheme {
        BackupUI(Date(), {}, {}){}
    }
}