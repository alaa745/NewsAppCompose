// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
//    id("com.google.gms.google-services") version "4.4.1" apply false

}

buildscript {
    val hilt_version = "2.51.1"

    dependencies {
        // Add the following line
//        classpath ("com.google.gms:google-services:4.4.1")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")

    }
}