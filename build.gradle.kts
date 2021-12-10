import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform") version Versions.KOTLIN
    application
    kotlin("plugin.serialization") version Versions.KOTLIN
}

group = "com.zachrobweig"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        withJava()
    }
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}")
                implementation("io.ktor:ktor-client-core:${Versions.KTOR}")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization:${Versions.KTOR}")
                implementation("io.ktor:ktor-server-core:${Versions.KTOR}")
                implementation("io.ktor:ktor-server-netty:${Versions.KTOR}")
                implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK}")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:${Versions.KMONGO}")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:${Versions.KTOR}")
                implementation("io.ktor:ktor-client-json:${Versions.KTOR}")
                implementation("io.ktor:ktor-client-serialization:${Versions.KTOR}")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:${Versions.REACT_WRAPPERS}")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:${Versions.REACT_WRAPPERS}")
                implementation(npm("collections", Versions.COLLECTIONS_JS))
            }
        }
    }
}

application {
    mainClass.set("ServerKt")
}

// include JS artifacts in any JAR we generate
tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}

tasks.named<Copy>("jvmProcessResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
