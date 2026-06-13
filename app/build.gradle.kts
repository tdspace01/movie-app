plugins {
    id("convention.android.application")
    id("convention.compose")
}

android {
    namespace = "com.example.movieapp"

    defaultConfig {
        applicationId = "com.example.movieapp"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}
