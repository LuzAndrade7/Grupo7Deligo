plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")  // Agregar esto para habilitar Google Services
}

android {
    namespace = "com.example.deligo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.deligo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Firebase BOM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")

    // Otras dependencias necesarias
    implementation("com.google.android.gms:play-services-auth:20.1.0")

    // Otras dependencias
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.google.firebase:firebase-firestore-ktx")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
