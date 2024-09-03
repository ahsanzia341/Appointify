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
import com.ahsan.core.DestinationRoute

enum class BottomBarDestination(val route: String,
                                @StringRes val title: Int,
                                val unFilledIcon: ImageVector,
                                val filledIcon: ImageVector) {
    HOME(route = DestinationRoute.HOME_ROUTE,
        title = com.ahsan.composable.R.string.home,
        unFilledIcon = Icons.Outlined.Home,
        filledIcon = Icons.Filled.Home),

    CLIENT(route = DestinationRoute.CLIENT_LIST_ROUTE,
    title = com.ahsan.composable.R.string.client,
    unFilledIcon = Icons.Outlined.Person,
    filledIcon = Icons.Filled.Person),

    SERVICE(route = DestinationRoute.SERVICE_LIST_ROUTE,
    title = com.ahsan.composable.R.string.service,
    unFilledIcon = Icons.Outlined.MailOutline,
    filledIcon = Icons.Filled.MailOutline),

    APPOINTMENT_HISTORY(route = DestinationRoute.APPOINTMENT_HISTORY_ROUTE,
    title = com.ahsan.composable.R.string.history,
    unFilledIcon = Icons.Outlined.DateRange,
    filledIcon = Icons.Filled.DateRange),

    SETTINGS(route = DestinationRoute.SETTINGS_ROUTE,
    title = com.ahsan.composable.R.string.settings,
    unFilledIcon = Icons.Outlined.Settings,
    filledIcon = Icons.Filled.Settings)
}