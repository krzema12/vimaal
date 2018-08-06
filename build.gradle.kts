import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.60"
}

group = "it.krzeminski"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    testCompile("junit:junit:4.12")
    testCompile("org.specnaz:specnaz-kotlin-junit:1.4")
    testImplementation("io.mockk:mockk:1.8.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
