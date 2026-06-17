plugins {
    id("convention.android.library")
    id("convention.compose")
}

android {
    namespace = "com.example.movieapp.data"
}

dependencies{
    implementation(project(":domain"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
}