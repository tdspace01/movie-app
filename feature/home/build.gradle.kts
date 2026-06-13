plugins {
    id("convention.android.library")
    id("convention.compose")
}

android {
    namespace = "com.example.movieapp.home"
}

dependencies{
    implementation(project(":core:navigation"))
}
