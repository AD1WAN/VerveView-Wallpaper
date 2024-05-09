// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("com.android.library") version "8.1.1" apply false
    alias(libs.plugins.jetbrains) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false

}