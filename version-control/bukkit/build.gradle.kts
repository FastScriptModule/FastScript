plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(project(":common"))

    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.9")
    implementation("org.bstats:bstats-bukkit:1.7")
    implementation("me.scoretwo:commons-bukkit-plugin:2.0.3-SNAPSHOT")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}

tasks.processResources {
    from("src/main/resource") {
        include("plugin.yml")
        expand(mapOf(
            "name" to rootProject.name,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.bukkit.BukkitPlugin",
            "version" to rootProject.version,
            "description" to rootProject.description
        ))
    }
}