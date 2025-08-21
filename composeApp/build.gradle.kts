import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    kotlin("plugin.serialization") version "2.1.20"
    id("app.cash.sqldelight") version "2.1.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        val ktor_version = "3.1.3"
        val file_pick = "0.10.0-beta04"
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-android:$ktor_version")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
            implementation("app.cash.sqldelight:android-driver:2.1.0")
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:$ktor_version")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
            implementation("app.cash.sqldelight:native-driver:2.1.0")
        }
        commonMain.dependencies {
            val voyagerVersion = "1.1.0-beta02"
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-lifecycle-kmp:${voyagerVersion}")
            implementation("io.ktor:ktor-client-core:$ktor_version")
            implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
            implementation("io.ktor:ktor-server-default-headers:$ktor_version")
            implementation("com.russhwolf:multiplatform-settings:1.3.0")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
            implementation("app.cash.sqldelight:runtime:2.1.0")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.1")
            implementation("io.coil-kt.coil3:coil-compose:3.2.0")
            implementation("io.coil-kt.coil3:coil-svg:3.2.0")
            implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")
            implementation("io.github.vinceglb:filekit-core:$file_pick")
            implementation("io.github.vinceglb:filekit-dialogs-compose:$file_pick")
            implementation("io.github.vinceglb:filekit-coil:$file_pick")
            implementation("io.github.dokar3:sonner:0.3.8")
            implementation("io.github.g0dkar:qrcode-kotlin:4.1.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("io.ktor:ktor-client-apache5:$ktor_version")
            implementation("app.cash.sqldelight:sqlite-driver:2.1.0")
        }
    }
}

android {
    namespace = "org.gangoogle.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.gangoogle.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    lint {
        disable += setOf("NullSafeMutableLiveData")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.gangoogle.project.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.gangoogle.project"
            packageVersion = "1.0.0"
            linux {
                modules("jdk.security.auth")
            }
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.gangoogle.database")
        }
    }
}