plugins {
    id("com.android.application")
    kotlin("android")

}
apply(plugin = "com.google.gms.google-services")
apply(plugin = "com.google.firebase.crashlytics")
apply(plugin = "com.google.firebase.firebase-perf")

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId("com.kastik.moriapaneladikon")
        minSdkVersion(19)
        targetSdkVersion(30)
        versionCode = 3
        versionName = "1.0-alpha"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    signingConfigs {
        create("releaseSign") {
            storeFile = file("AndroidStudioSigningKey.jks")
            storePassword = "214221121236547k"
            keyAlias = "MoriaKey"
            keyPassword = "214221121236547k"
            // v1SigningEnabled(true)
            //v2SigningEnabled = true
        }
    }



    buildTypes {
        getByName("release") {
            getDefaultProguardFile("proguard-android-optimize.txt")
            isMinifyEnabled = true
            proguardFiles("proguard-rules-release-test.pro")
            multiDexEnabled = false
            renderscriptDebuggable(false)
            debuggable(false)
            jniDebuggable(false)
            resValue("string", "InterstitialAdId", "ca-app-pub-8103443984633175/9190337650")
            resValue("string", "app_name", "Μορια")
            resValue("string", "provider", "com.kastik.moriapaneladikon.provider")
            signingConfig = signingConfigs.getByName("releaseSign")
        }

        getByName("debug") {
            getDefaultProguardFile("proguard-android-optimize.txt")
            minifyEnabled(false)
            proguardFiles("proguard-rules-release-test.pro")
            multiDexEnabled = true
            renderscriptDebuggable(true)
            renderscriptOptimLevel = 3
            debuggable(true)
            jniDebuggable(true)
            applicationIdSuffix = ".debug"
            resValue("string", "InterstitialAdId" , "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "app_name", "Μορια Debug")
            resValue("string", "provider", "com.kastik.moriapaneladikon.debug.provider")
            signingConfig = signingConfigs.getByName("releaseSign")
        }

    }




    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    compileOptions {
        sourceCompatibility("1.8")
        targetCompatibility("1.8")
    }
    flavorDimensions
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("com.google.android.material:material:1.3.0")
    implementation("com.google.firebase:firebase-ads:19.7.0")
    implementation("org.jetbrains:annotations:20.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
    implementation ("com.google.firebase:firebase-analytics:18.0.2")
    implementation ("com.android.support:multidex:1.0.3")
    implementation ("com.google.firebase:firebase-crashlytics:17.3.1")
    implementation ("com.google.firebase:firebase-perf:19.1.1")
    implementation ("com.google.firebase:firebase-database:19.6.0")
    implementation ("com.firebaseui:firebase-ui-database:6.2.0")
    implementation ("com.firebaseui:firebase-ui-firestore:4.1.0")
    implementation ("com.google.firebase:firebase-storage:19.2.1")
    implementation ("com.google.firebase:firebase-firestore:22.1.0")
    implementation ("com.firebaseui:firebase-ui-auth:6.2.0")
    implementation ("androidx.core:core-ktx:1.3.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.30")
    implementation("com.google.android.gms:play-services-auth:19.0.0")

}
repositories {
    mavenCentral()
}