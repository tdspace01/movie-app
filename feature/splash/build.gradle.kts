plugins {
    id("convention.android.library")
    id("convention.compose")
}

android {
    namespace = "com.example.movieapp.splash"
}

dependencies{
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))
}
