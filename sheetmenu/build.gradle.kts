import org.gradle.internal.impldep.bsh.commands.dir
import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
import org.gradle.script.lang.kotlin.compile
import java.io.File
import build.*
import org.apache.commons.logging.LogFactory.release

apply {
    plugin("com.android.library")
    plugin("kotlin-android")
    plugin("com.github.dcendents.android-maven")
}


//apply plugin: 'com.android.library'
//apply plugin: 'kotlin-android'
//apply plugin: 'com.github.dcendents.android-maven'

group = 'com.github.whalemare'

//sourceCompatibility = 1.7
//targetCompatibility = 1.7

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.3"
    defaultConfig {
        minSdkVersion 19
        targetSdkVersion 25
        versionCode 1
        versionName app_version

                testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile ('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion . VERSION_1_7
                targetCompatibility JavaVersion . VERSION_1_7
    }
}

dependencies {
    compile fileTree (dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support:design:25.3.1'
    compile "org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
//    compile 'com.github.jitpack:gradle-simple:master-SNAPSHOT'
}
repositories {
    mavenCentral()
}

// build a jar with source files
task sourcesJar (type: Jar) {
    from android . sourceSets . main . java . srcDirs
            classifier = 'sources'
}

task javadoc (type: Javadoc) {
    failOnError  false
    source = android.sourceSets.main.java.sourceFiles
    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
    classpath += configurations.compile
}

// build a jar with javadoc
task javadocJar (type: Jar, dependsOn: javadoc) {
    classifier = 'javadoc'
    from javadoc . destinationDir
}

artifacts {
    archives sourcesJar
            archives javadocJar
}
