package com.ahsan.smartappointment

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ahsan.core.AppRoute.AppointmentHistoryRoute
import com.ahsan.core.AppRoute.ClientListRoute
import com.ahsan.core.AppRoute.HomeRoute
import com.ahsan.core.AppRoute.ServiceListRoute
import com.ahsan.core.AppRoute.SettingsRoute

enum class BottomBarDestination(val route: Any,
                                @StringRes val title: Int,
                                val unFilledIcon: ImageVector,
                                val filledIcon: ImageVector) {
    HOME(
        route = HomeRoute,
        title = com.ahsan.composable.R.string.home,
        unFilledIcon = Icons.Outlined.Home,
        filledIcon = Icons.Filled.Home
    ),

    CLIENT(
        route = ClientListRoute,
        title = com.ahsan.composable.R.string.client,
        unFilledIcon = Icons.Outlined.Person,
        filledIcon = Icons.Filled.Person
    ),

    SERVICE(
        route = ServiceListRoute,
        title = com.ahsan.composable.R.string.service,
        unFilledIcon = Icons.Outlined.MailOutline,
        filledIcon = Icons.Filled.MailOutline
    ),

    APPOINTMENT_HISTORY(
        route = AppointmentHistoryRoute,
        title = com.ahsan.composable.R.string.history,
        unFilledIcon = Icons.Outlined.DateRange,
        filledIcon = Icons.Filled.DateRange
    ),

    SETTINGS(
        route = SettingsRoute,
        title = com.ahsan.composable.R.string.settings,
        unFilledIcon = Icons.Outlined.Settings,
        filledIcon = Icons.Filled.Settings
    )
}