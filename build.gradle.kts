plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    repositories {
        google()  // Repositorio de Google
        mavenCentral()
    }
    dependencies {
        // Clase del plugin de Google Services
        classpath("com.google.gms:google-services:4.3.15")
    }
}
