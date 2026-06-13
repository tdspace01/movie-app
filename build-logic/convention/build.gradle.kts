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
    compileOnly(libs.plugins.android.application.toDep())
    compileOnly(libs.plugins.android.library.toDep())
    compileOnly(libs.plugins.kotlin.android.toDep())
    compileOnly(libs.plugins.kotlin.compose.toDep())
    compileOnly(libs.plugins.google.ksp.toDep())
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
    }
}