pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SmartAppointment"
include(":app")
include(":feature:home")
include(":common:composable")
include(":data")
include(":domain")
include(":feature:appointment")
include(":core")
include(":feature:client")
include(":feature:appointmenthistory")
include(":feature:authentication")
include(":feature:setting")
include(":feature:service")
include(":feature:webview")
include(":feature:currency")
include(":feature:accountsettings")
include(":feature:changepassword")
include(":feature:business")
include(":feature:backup")
include(":feature:businessdetail")
include(":feature:feedback")
