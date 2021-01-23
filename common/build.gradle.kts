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

    implementation("org.jetbrains.kotlin:kotlin-script-util:${KotlinVersion.CURRENT}")
    implementation("org.jetbrains.kotlin:kotlin-compiler:${KotlinVersion.CURRENT}")
    implementation("commons-cli:commons-cli:1.4")

    implementation("commons-io:commons-io:2.7")
    implementation("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-command:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}