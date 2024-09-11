package com.ahsan.core

object DestinationRoute {
    const val HOME_ROUTE = "home_route"
    const val CREATE_APPOINTMENT_ROUTE = "create_appointment/{${PassedKey.ID}}"
    const val UPDATE_APPOINTMENT_ROUTE = "update_appointment/{${PassedKey.ID}}"
    const val APPOINTMENT_DETAIL_ROUTE = "appointment_detail/{${PassedKey.ID}}"
    const val CLIENT_LIST_ROUTE = "client_list"
    const val CREATE_CLIENT_ROUTE = "create_client"
    const val SELECT_CLIENT_ROUTE = "select_client"
    const val SELECT_SERVICES_ROUTE = "select_services"
    const val APPOINTMENT_HISTORY_ROUTE = "appointment_history_route"
    const val WELCOME_ROUTE = "welcome_route"
    const val LOGIN_ROUTE = "login_route"
    const val REGISTER_ROUTE = "register_route"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password_route"
    const val SERVICE_LIST_ROUTE = "service_list_route"
    const val SERVICE_SELECT_ROUTE = "service_select_route"
    const val SERVICE_CREATE_ROUTE = "service_create_route/{${PassedKey.ID}}"
    const val SERVICE_DETAIL_ROUTE = "service_detail_route/{${PassedKey.ID}}"
    const val SETTINGS_ROUTE = "settings_route"
    object PassedKey{
        const val ID = "id"
        const val CLIENT_ID = "client_id"
        const val SERVICE_IDS = "service_ids"
    }
}