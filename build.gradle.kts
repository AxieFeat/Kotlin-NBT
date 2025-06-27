plugins {
    kotlin("jvm") version "2.0.21"
    id("maven-publish")
}

group = "com.github.AxieFeat"
version = "1.3"

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

            groupId = "com.github.AxieFeat"
            artifactId = project.name

            pom {
                name.set(project.properties["POM_NAME"].toString())
                description.set(project.description)
            }
        }
    }
}