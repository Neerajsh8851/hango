import java.net.URI

include(":kotlinutils")


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
//    includeBuild("../build-logic")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}

rootProject.name = "hango"
include(":app")
