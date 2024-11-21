plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.android.msd.capstone.project.gardennerds"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.msd.capstone.project.gardennerds"
         minSdk = 31
        //minSdk = 30
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

    // Android Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // AR Libraries
    implementation(libs.ar.core)
    // Sceneform for AR
    implementation(libs.sceneform.ux)

    // Gson and Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Picasso, Glide, and Shimmer
    implementation(libs.picasso)
    implementation(libs.glide)
    implementation(libs.shimmer)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    // Location
    implementation(libs.fused.location)

    // ViewPager and Wearable
    implementation(libs.viewpager2)


    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}