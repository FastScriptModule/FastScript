plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
}


repositories {
    maven("https://nexus.velocitypowered.com/repository/velocity-artifacts-snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(project(":common"))


    compileOnly("com.velocitypowered:velocity-api:1.0.11-SNAPSHOT")
    implementation("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT")
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
        include("velocity-plugin.json")
        expand(mapOf(
            "modid" to "fastscript",
            "name" to project.name,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.velocity.VelocityPlugin",
            "version" to project.version,
            "description" to project.description
        ))
    }
}