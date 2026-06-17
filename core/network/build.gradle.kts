plugins {
    id("convention.android.library")
}

android{
    namespace = "com.example.movieapp.network"
}

dependencies{
    implementation(project(":domain"))
    implementation(project(":core:common"))
}
