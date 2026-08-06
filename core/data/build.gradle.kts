import org.gradle.kotlin.dsl.dependencies

plugins {
    id("convention.android.library")
    id("convention.compose")
}

android {
    namespace = "com.example.movieapp.data"
}

dependencies{
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(libs.androidx.paging.runtime)
}