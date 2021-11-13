plugins {
    kotlin("multiplatform") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
    application
}

group = "me.rafaavc"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "15"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
        withJava()
    }
    js(LEGACY) {
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
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:1.6.0")
                implementation("io.ktor:ktor-html-builder:1.6.0")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.264-kotlin-1.5.31")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.264-kotlin-1.5.31")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-pre.264-kotlin-1.5.31")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}