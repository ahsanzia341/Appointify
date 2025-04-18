plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("/Users/ahsanzia/Documents/appointify/store_keystore")
            storePassword = "TestPassword1@"
            keyAlias = "ahsan"
            keyPassword = "TestPassword1@"
        }
    }
    namespace = "com.ahsan.smartappointment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ahsan.smartappointment"
        minSdk = 24
        targetSdk = 35
        versionCode = 10
        versionName = "0.10"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.hilt)
    implementation(libs.firebase.crashlytics)
    ksp(libs.hilt.compiler)
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":common:composable")))
    implementation(project(mapOf("path" to ":feature:accountsettings")))
    implementation(project(mapOf("path" to ":feature:authentication")))
    implementation(project(mapOf("path" to ":feature:backup")))
    implementation(project(mapOf("path" to ":feature:business")))
    implementation(project(mapOf("path" to ":feature:changepassword")))
    implementation(project(mapOf("path" to ":feature:home")))
    implementation(project(mapOf("path" to ":feature:appointment")))
    implementation(project(mapOf("path" to ":feature:appointmenthistory")))
    implementation(project(mapOf("path" to ":feature:client")))
    implementation(project(mapOf("path" to ":feature:setting")))
    implementation(project(mapOf("path" to ":feature:service")))
    implementation(project(mapOf("path" to ":feature:currency")))
    implementation(project(mapOf("path" to ":feature:webview")))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
