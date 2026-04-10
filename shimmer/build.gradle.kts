plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
    signing
    id("com.gradleup.nmcp") version "0.0.7"
}

group = "io.github.mallikarjunpatelsh"
version = "1.0.1"

android {
    namespace = "com.mallikarjunpatelsh.shimmer"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Coil — optional. If consumer doesn't use ShimmerAsyncImage they can
    // exclude it; declared as `compileOnly` so it doesn't force the dep.
    compileOnly(libs.coil.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "io.github.mallikarjunpatelsh"
                artifactId = "shimmer-compose"
                version = "1.0.1"

                pom {
                    name.set("Shimmer Compose")
                    description.set("Shimmer effect for Jetpack Compose")
                    url.set("https://github.com/mallikarjunpatelsh/shimmer-compose")

                    licenses {
                        license {
                            name.set("Apache-2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("mallikarjunpatelsh")
                            name.set("Mallikarjun Patel")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/mallikarjunpatelsh/shimmer-compose.git")
                        developerConnection.set("scm:git:ssh://github.com/mallikarjunpatelsh/shimmer-compose.git")
                        url.set("https://github.com/mallikarjunpatelsh/shimmer-compose")
                    }
                }
            }
        }
    }
}

nmcp {
    publishAllPublications {
        username = System.getenv("SONATYPE_USERNAME")
        password = System.getenv("SONATYPE_PASSWORD")
        publicationType = "AUTOMATIC"
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("MAVEN_GPG_PRIVATE_KEY"),
        System.getenv("MAVEN_GPG_PASSPHRASE")
    )
    sign(publishing.publications)
}