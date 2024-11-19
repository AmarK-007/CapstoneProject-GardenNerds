plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.android.msd.capstone.project.gardennerds"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.msd.capstone.project.gardennerds"
        minSdk = 31
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core)
    //implementation(libs.sceneform.ux)

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Gson for json converter
    implementation("com.google.code.gson:gson:2.10.1")

    // Sceneform for AR
    implementation("com.google.ar:core:1.31.0")

    //retrofit libs
    implementation(libs.retrofit)
    implementation(libs.converter.gson.v290)

    //Glide
    implementation(libs.glide)

    //Location
    implementation(libs.fused.location)

}