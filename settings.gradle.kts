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
<<<<<<< HEAD
include(":core:ui")
include(":core:data")
include(":core:domain")
include(":core:common")
=======
include(":core:data")
include(":core:common")
include(":core:domain")
>>>>>>> fc8400e (Networking Branch: Api calls added)
include(":feature:home:")
include(":core:network:")
include(":feature:splash:")
include(":core:navigation:")
<<<<<<< HEAD
include(":feature:favourite:")
include(":core:designsystem:")
=======
include(":core:designsystem:")
include(":feature:favourite:")
>>>>>>> fc8400e (Networking Branch: Api calls added)
include(":feature:moviedetail:")
