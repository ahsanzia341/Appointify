package com.ahsan.core

object DestinationRoute {
    const val HOME_ROUTE = "home_route"
    const val CREATE_APPOINTMENT_ROUTE = "create_appointment/{${PassedKey.ID}}"
    const val UPDATE_APPOINTMENT_ROUTE = "update_appointment/{${PassedKey.ID}}"
    const val APPOINTMENT_DETAIL_ROUTE = "appointment_detail/{${PassedKey.ID}}"
    const val ACCOUNT_SETTING_ROUTE = "account_setting_route"
    const val EDIT_ACCOUNT_ROUTE = "edit_account_route"
    const val CREATE_BUSINESS_ROUTE = "create_business_route"
    const val CHANGE_PASSWORD_ROUTE = "change_password_route"
    const val CLIENT_LIST_ROUTE = "client_list"
    const val CURRENCY_ROUTE = "currency_route"
    const val CREATE_CLIENT_ROUTE = "create_client/{${PassedKey.ID}}"
    const val APPOINTMENT_HISTORY_ROUTE = "appointment_history_route"
    const val WELCOME_ROUTE = "welcome_route"
    const val LOGIN_ROUTE = "login_route"
    const val REGISTER_ROUTE = "register_route"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password_route"
    const val SERVICE_LIST_ROUTE = "service_list_route"
    const val SERVICE_CREATE_ROUTE = "service_create_route/{${PassedKey.ID}}"
    const val SETTINGS_ROUTE = "settings_route"
    const val WEB_VIEW_ROUTE = "web_view_route/{${PassedKey.URL}}"

    object PassedKey {
        const val ID = "id"
        const val URL = "url"
    }
}