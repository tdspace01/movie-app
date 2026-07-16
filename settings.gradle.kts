import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "movieapp"
include(":app")
include(":core:ui")
include(":core:data")
include(":core:common")
include(":core:domain")
include(":feature:home:")
include(":core:network:")
include(":feature:splash:")
include(":core:navigation:")
include(":core:designsystem:")
include(":feature:favourite:")
include(":feature:moviedetail:")
