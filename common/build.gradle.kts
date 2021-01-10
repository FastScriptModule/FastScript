plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
}

repositories {
}

dependencies {
    implementation(project(":common"))

    compileOnly("org.apache.commons:commons-lang3:3.10")
    compileOnly("com.google.code.gson:gson:2.8.6")
    compileOnly("org.slf4j:slf4j-log4j12:1.7.30")
    implementation("commons-io:commons-io:2.7")
    implementation("me.scoretwo:commons-syntaxes:2.0.1-SNAPSHOT")
    implementation("me.scoretwo:commons-command:2.0.1-SNAPSHOT")
    implementation("me.scoretwo:commons-server:2.0.1-SNAPSHOT")
    implementation("me.scoretwo:commons-bukkit-configuration:2.0.1-SNAPSHOT")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}