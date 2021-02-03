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

    implementation("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-command:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}")
}


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        include(dependency("org.jetbrains.kotlin:kotlin-stdlib"))

        include(dependency(":FastScript-common"))
        include(dependency(":version-control:FastScript-bukkit"))
        include(dependency(":version-control:FastScript-bungee"))
        include(dependency(":version-control:FastScript-sponge"))
        include(dependency(":version-control:FastScript-velocity"))

        include(dependency("me.scoretwo:commons-velocity-plugin:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-sponge-plugin:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-bungee-plugin:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-bukkit-plugin:${rootProject.extra.get("commonsVersion")}"))

        include(dependency("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT"))
        include(dependency("org.bstats:bstats-bukkit:1.7"))

        include(dependency("commons-io:commons-io:2.7"))

        include(dependency("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-command:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}"))
    }
    relocate("org.apache","me.scoretwo.utils.libs.org.apache")
    relocate("org.bstats","me.scoretwo.utils.libs.org.bstats")

    classifier = null
}