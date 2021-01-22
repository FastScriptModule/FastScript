plugins {
    kotlin("jvm") version "1.4.21" apply false
    id("org.jetbrains.dokka") version "1.4.10.2" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1" apply false
    id("com.github.johnrengelman.shadow") version "6.1.0" apply false
    id("net.kyori.blossom") version "1.1.0" apply false
    id("maven")
    id("maven-publish")
}

group = "me.scoretwo"
version = "1.0.1-SNAPSHOT"
description = "FastScript is a Spigot plugin, which can run JavaScript-based scripts more efficiently."

defaultTasks = mutableListOf("publishToMavenLocal")

extra.apply {
    set("commonsVersion", "2.0.3-SNAPSHOT")
}

subprojects {
    group = rootProject.group
    version = rootProject.version
    description = rootProject.description

    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
        maven("https://repo.lucko.me/")
        maven("http://repo.iroselle.com/snapshots/")
    }
}

/*
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        include(dependency("me.scoretwo:commons-syntaxes:1.2-SNAPSHOT"))
        include(dependency("me.scoretwo:commons-configuration:1.2-SNAPSHOT"))
        include(dependency("org.bstats:bstats-bukkit:1.7"))
        include(dependency("commons-io:commons-io:2.7"))
        include(dependency("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT"))
        include(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        include(dependency(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
    }
    relocate("org.bstats", "me.scoretwo.utils.libs.bstats")
    relocate("org.apache.commons.io", "me.scoretwo.utils.libs.apache.commons.io")
}
