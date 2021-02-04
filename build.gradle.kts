plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.dokka") version "1.4.10.2" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1" apply false
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("net.kyori.blossom") version "1.1.0" apply false
    id("maven")
    id("maven-publish")
}

group = "me.scoretwo"
version = "1.0.1-SNAPSHOT"
description = "FastScript is a Spigot plugin, which can run JavaScript-based scripts more efficiently."

defaultTasks = mutableListOf("publishToMavenLocal")

extra.apply {
    set("commonsVersion", "2.0.7-SNAPSHOT")
    set("kotlinVersion", "1.4.30")
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
        maven("https://repo.lucko.me/")
        maven("http://repo.iroselle.com/snapshots/")
        maven("https://nexus.velocitypowered.com/repository/velocity-artifacts-snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
        maven("https://repo.codemc.io/repository/maven-snapshots/")
        maven("https://repo.spongepowered.org/maven")
        maven("https://jitpack.io")
        maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
        maven("https://repo.codemc.io/repository/maven-snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
        maven("https://repo.codemc.io/repository/maven-snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
    description = rootProject.description

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}