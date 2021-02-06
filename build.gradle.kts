plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.dokka") version "1.4.10.2"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("net.kyori.blossom") version "1.1.0"
    id("maven")
    id("maven-publish")
}

group = "me.scoretwo"
version = "1.0.1-SNAPSHOT"
description = "FastScript is a Spigot plugin, which can run JavaScript-based scripts more efficiently."

defaultTasks = mutableListOf("ShadowJar", "publishToMavenLocal")

extra.apply {
    set("commonsVersion", "2.0.8-SNAPSHOT")
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

    group = rootProject.group
    version = rootProject.version
    description = rootProject.description

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}



dependencies {
    implementation(project(":FastScript-common"))
    implementation(project(":version-control:FastScript-bukkit"))
    implementation(project(":version-control:FastScript-bungee"))
    implementation(project(":version-control:FastScript-sponge"))
    implementation(project(":version-control:FastScript-velocity"))

    implementation("me.scoretwo:commons-sponge-plugin:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-bungee-plugin:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-bukkit-plugin:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-velocity-plugin:${rootProject.extra.get("commonsVersion")}")

    implementation("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:1.7")
    implementation("commons-io:commons-io:2.7")
    implementation("commons-lang:commons-lang:2.6")

    implementation("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-command:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}")
}


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        include(dependency("org.jetbrains.kotlin:kotlin-stdlib"))

        include(dependency(":FastScript-common"))
        include(dependency(":FastScript-bukkit"))
        include(dependency(":FastScript-bungee"))
        include(dependency(":FastScript-sponge"))
        include(dependency(":FastScript-velocity"))

        include(dependency("me.scoretwo:commons-velocity-plugin:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-sponge-plugin:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-bungee-plugin:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-bukkit-plugin:${rootProject.extra.get("commonsVersion")}"))

        include(dependency("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT"))
        include(dependency("org.bstats:bstats-bukkit:1.7"))

        include(dependency("commons-io:commons-io:2.7"))
        include(dependency("commons-lang:commons-lang:2.6"))

        include(dependency("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-command:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}"))
    }
    relocate("kotlin", "me.scoretwo.utils.shaded.kotlin")
    relocate("org.apache","me.scoretwo.utils.shaded.org.apache")
    relocate("org.bstats","me.scoretwo.utils.shaded.org.bstats")

    exclude("META-INF/versions/9/module-info.class")
    exclude("META-INF/*.kotlin_module")
    exclude("mojang-translations/*.*")

    classifier = null
}

tasks.processResources {
    from("src/main/resources") {
        include("plugin.yml")
        expand(mapOf(
            "name" to rootProject.name,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.bukkit.BukkitBootStrap",
            "version" to rootProject.version,
            "description" to rootProject.description
        ))
    }
    from("src/main/resources") {
        include("bungee.yml")
        expand(mapOf(
            "name" to rootProject.name,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.bungee.BungeeBootStrap",
            "version" to project.version,
            "description" to project.description
        ))
    }
    from("src/main/resources") {
        include("mcmod.info")
        expand(mapOf(
            "id" to rootProject.name.toLowerCase(),
            "name" to rootProject.name,
            "version" to project.version,
            "description" to project.description
        ))
    }
    from("src/main/resources") {
        include("velocity-plugin.json")
        expand(mapOf(
            "id" to rootProject.name.toLowerCase(),
            "name" to rootProject.name,
            "version" to project.version,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.velocity.VelocityBootStrap",
            "description" to project.description
        ))
    }
}