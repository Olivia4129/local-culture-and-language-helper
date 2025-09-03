plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.olivia.localcultureandlanguagehelper"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.olivia.localcultureandlanguagehelper"
        minSdk = 31
        targetSdk = 36
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- AndroidX ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // --- Firebase (using BoM, no version conflicts) ---
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")

    // --- Supabase ---
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.1.0")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.1.0")
    implementation("io.github.jan-tennert.supabase:storage-kt:2.1.0")
    implementation("io.ktor:ktor-client-okhttp:2.3.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // --- Coil ---
    implementation("io.coil-kt:coil-compose:2.4.0")

    // --- Navigation ---
    implementation("androidx.navigation:navigation-compose:2.9.2")
    implementation("androidx.navigation:navigation-ui:2.7.7")

    // --- ViewModel ---
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.runtime.livedata)

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    //cloudinary
    implementation("com.cloudinary:cloudinary-android:2.3.1")
    //coil
    implementation("io.coil-kt:coil-compose:2.0.0")

}
