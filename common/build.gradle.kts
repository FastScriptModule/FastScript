plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
}

repositories {
    maven("http://repo.iroselle.com/snapshots/")
}

dependencies {
    implementation(project(":common"))

    compileOnly("org.apache.commons:commons-lang3:3.10")
    compileOnly("com.google.code.gson:gson:2.8.6")
    implementation("commons-io:commons-io:2.7")
    implementation("me.scoretwo:commons-syntaxes:2.0-SNAPSHOT")
    implementation("me.scoretwo:commons-bukkit-configuration:2.0-SNAPSHOT")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}