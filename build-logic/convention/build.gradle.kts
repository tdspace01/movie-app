import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.example.movieapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.plugins.android.application.toDep())
    implementation(libs.plugins.android.library.toDep())
    implementation(libs.plugins.kotlin.android.toDep())
    implementation(libs.plugins.kotlin.compose.toDep())
    implementation(libs.plugins.google.ksp.toDep())
    implementation(libs.plugins.kotlin.serialization.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "convention.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "convention.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("kotlinLibrary"){
            id = "convention.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("androidNetwork"){
            id = "convention.android.network"
            implementationClass = "AndroidNetworkConventionPlugin"
        }
    }
}