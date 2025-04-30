package com.ahsan.core

import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    data class WebViewRoute(val url: String) : AppRoute()

    @Serializable
    data object WelcomeRoute : AppRoute()

    @Serializable
    data object BackupRoute : AppRoute()

    @Serializable
    data object HomeRoute : AppRoute()

    @Serializable
    data object AccountSettingRoute : AppRoute()

    @Serializable
    data object LoginRoute : AppRoute()

    @Serializable
    data object RegisterRoute : AppRoute()

    @Serializable
    data object ChangePasswordRoute : AppRoute()

    @Serializable
    data class CreateBusinessRoute(val isEdit: Boolean = false) : AppRoute()

    @Serializable
    data class CreateTeamsRoute(val isEdit: Boolean = false) : AppRoute()

    @Serializable
    data object BusinessDetailRoute : AppRoute()

    @Serializable
    data object ServiceListRoute : AppRoute()

    @Serializable
    data object ForgotPasswordRoute : AppRoute()

    @Serializable
    data object SettingsRoute : AppRoute()

    @Serializable
    data object CurrencyRoute : AppRoute()

    @Serializable
    data object AppointmentHistoryRoute : AppRoute()

    @Serializable
    data object ClientListRoute : AppRoute()

    @Serializable
    data object EditAccountRoute : AppRoute()

    @Serializable
    data object CreateAppointmentRoute : AppRoute()

    @Serializable
    data class AppointmentDetailRoute(val id: Int) : AppRoute()

    @Serializable
    data object CreateClientRoute : AppRoute()

    @Serializable
    data object ServiceCreateRoute : AppRoute()

    @Serializable
    data object FeedbackRoute : AppRoute()
}
