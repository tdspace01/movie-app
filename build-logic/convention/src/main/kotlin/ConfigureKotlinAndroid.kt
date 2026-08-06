import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureAndroidApplication(extension: ApplicationExtension) {
    pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

    extension.apply {
        compileSdk = 37
        defaultConfig {
            minSdk = 24
            targetSdk = 37
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
    configureSharedKotlinAndDependencies()
}

internal fun Project.configureAndroidLibrary(extension: LibraryExtension) {
    pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

    extension.apply {
        compileSdk = 37
        defaultConfig {
            minSdk = 24
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
    configureSharedKotlinAndDependencies()
}

private fun Project.configureSharedKotlinAndDependencies() {
    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    dependencies {
        add("implementation", libs.findBundle("koin").get())
        add("implementation", libs.findBundle("retrofit").get())
        add("implementation", libs.findBundle("navigation").get())
        add("implementation", libs.findBundle("storage").get())
        add("testImplementation", libs.findBundle("test-unit").get())
        add("androidTestImplementation", libs.findBundle("test-instrumented").get())
    }
}

internal fun Project.configureComposeDependencies() {
    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("implementation", libs.findBundle("compose").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
    }
}