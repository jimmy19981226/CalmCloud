plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

// Endpoint URLs are configuration; provider credentials stay on a server.
fun quotedProperty(name: String, defaultValue: String): String {
    val value = providers.gradleProperty(name).orElse(defaultValue).get()
    return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
}

android {
    namespace = "com.example.calmcloud"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.calmcloud"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BACKEND_BASE_URL", quotedProperty("CALMCLOUD_API_BASE_URL", "http://10.0.2.2:3000/"))
        buildConfigField("String", "CHAT_BASE_URL", quotedProperty("CALMCLOUD_CHAT_BASE_URL", ""))
        buildConfigField("String", "CHAT_MODEL", quotedProperty("CALMCLOUD_CHAT_MODEL", "gpt-3.5-turbo"))
    }

    buildFeatures { buildConfig = true }

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation ("androidx.gridlayout:gridlayout:1.0.0")


    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Retrofit dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp dependencies (Retrofit internally uses OkHttp for networking)
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}