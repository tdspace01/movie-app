plugins {
    id("convention.android.library")
    id("convention.compose")
}

android {
    namespace = "com.example.movieapp.moviedetail"
}

dependencies{
    implementation(project(":core:navigation"))
}
