plugins {
    id("convention.android.network")
}

android{
    namespace = "com.example.movieapp.network"
}

dependencies{
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
}
