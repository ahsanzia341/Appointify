package com.ahsan.accountsettings

import com.ahsan.core.BaseViewModel

class AccountSettingViewModel(): BaseViewModel<ViewState, AccountSettingEvent>() {
    override fun onTriggerEvent(event: AccountSettingEvent) {

    }

    init {
        updateState(ViewState(settings = settings))
    }
}