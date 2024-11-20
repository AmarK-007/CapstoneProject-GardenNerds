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
    implementation(libs.gson)

    // Sceneform for AR
//    implementation(libs.ar.core)


    implementation(libs.picasso)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.shimmer)

//retrofit libs
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //viewpager
    implementation(libs.viewpager2)

}