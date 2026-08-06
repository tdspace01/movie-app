plugins {
    id("convention.android.library")
    id("convention.compose")
}

android {
    namespace = "com.example.movieapp.favourite"
}

dependencies{
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))
}