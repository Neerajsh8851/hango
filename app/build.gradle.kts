@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.plugin)
}

android {
    namespace = "desidev.hango"
    compileSdk = 34

    defaultConfig {
        applicationId = "desidev.hango"
        minSdk = 24
        versionCode = 1
        versionName = "1.0"

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
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(group = "desidev.customnavigation", name = "custom-navigation")
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.lifecyle)
    implementation(libs.androidx.composeActivity)

    implementation(platform(libs.androidx.composeBom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.composeUi)
    debugImplementation(libs.androidx.composeUiTooling)
    implementation(libs.androidx.composeUiToolingPreview)
    implementation(libs.androidx.compose.uiGraphics)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.serialization.gson)

    testImplementation(libs.junit.test)
    androidTestImplementation(libs.androidx.junit.test)
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform(libs.androidx.composeBom))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation(libs.androidx.composeUiTooling)
}
