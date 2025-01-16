plugins {
    kotlin("jvm") version "2.0.21"
}

group = "xyz.arial.nbt"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:26.0.1")
    implementation("org.pcollections:pcollections:4.0.0")
}