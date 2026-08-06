plugins {
    id("convention.kotlin.library")
}

dependencies{
    implementation(project(":core:common"))
    implementation(libs.coroutines.android)
    implementation(libs.androidx.paging.common)
}