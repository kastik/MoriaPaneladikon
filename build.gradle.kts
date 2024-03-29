// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
buildscript {
    //ext.kotlin_version = '1.4.21-2'
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("com.google.gms:google-services:4.3.5")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.5.0")
        classpath("com.google.firebase:perf-plugin:1.3.4")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions { useIR = true }
}


