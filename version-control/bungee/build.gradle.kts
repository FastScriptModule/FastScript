plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
}


repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(project(":common"))
    implementation("me.scoretwo:commons-bungee-plugin:2.0.3-SNAPSHOT")

    compileOnly("net.md-5:bungeecord-api:1.16-R0.4-SNAPSHOT")
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
        include("bungee.yml")
        expand(mapOf(
            "name" to project.name,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.bungee.BungeePlugin",
            "version" to project.version,
            "description" to project.description
        ))
    }
}