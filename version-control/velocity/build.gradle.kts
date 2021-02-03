plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
    id("net.kyori.blossom")
}


repositories {
    maven("https://nexus.velocitypowered.com/repository/velocity-artifacts-snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

blossom {
    replaceTokenIn("src/main/kotlin/me/scoretwo/fastscript/velocity/VelocityBootStrap.kt")
    replaceToken("%%id%%", project.name.toLowerCase())
    replaceToken("%%name%%", project.name)
    replaceToken("%%version%%", project.version)
    replaceToken("%%description%%", project.description)
}

dependencies {
    implementation(project(":common"))

    compileOnly("com.velocitypowered:velocity-api:1.0.11-SNAPSHOT")
    implementation("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT")
    implementation("me.scoretwo:commons-velocity-plugin:${rootProject.extra.get("commonsVersion")}")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        include(dependency("org.jetbrains.kotlin:kotlin-stdlib"))

        include(dependency("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT"))
        include(dependency("me.scoretwo:commons-velocity-plugin:${rootProject.extra.get("commonsVersion")}"))
    }

    classifier = null
}

tasks.processResources {
    from("src/main/resource") {
        include("velocity-plugin.json")
        expand(mapOf(
            "id" to project.name.toLowerCase(),
            "name" to project.name,
            "version" to project.version,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.velocity.VelocityBootStrap",
            "description" to project.description
        ))
    }
}
/*
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
}*/
