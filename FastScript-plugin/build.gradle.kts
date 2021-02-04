import org.gradle.kotlin.dsl.support.compileKotlinScriptModuleTo

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
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