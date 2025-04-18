package com.ahsan.backup

import com.ahsan.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(): BaseViewModel<ViewState, BackupEvent>() {
    override fun onTriggerEvent(event: BackupEvent) {

    }

}