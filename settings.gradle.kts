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
