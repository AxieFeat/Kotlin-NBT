plugins {
    kotlin("jvm") version "2.0.21"
    id("maven-publish")
}

group = "xyz.axie.nbt"
version = "1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:26.0.1")
    implementation("org.pcollections:pcollections:4.0.0")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["kotlin"])

            groupId = "xyz.axie.nbt"
            artifactId = project.name

            pom {
                name.set(project.properties["POM_NAME"].toString())
                description.set(project.description)
            }
        }
    }
}