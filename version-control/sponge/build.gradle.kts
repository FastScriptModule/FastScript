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
    maven("https://repo.spongepowered.org/maven")
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/sonatype-nexus-snapshots/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

blossom {
    replaceTokenIn("src/main/kotlin/me/scoretwo/fastscript/sponge/SpongePlugin.kt")
    replaceToken("%%version%%", rootProject.version)
    replaceToken("%%description%%", rootProject.description)
}

dependencies {
    implementation(project(":common"))

    compileOnly("org.spongepowered:spongeapi:7.3.0")
    compileOnly("com.github.rojo8399:PlaceholderAPI:master-SNAPSHOT")
    implementation("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT")
    implementation("me.scoretwo:commons-sponge-plugin:${rootProject.extra.get("commonsVersion")}")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}

/*
tasks.processResources {
    from("src/main/resource") {
        include("mcmod.info")
        expand(mapOf(
            "modid" to "fastscript",
            "name" to project.name,
            "version" to project.version,
            "description" to project.description
        ))
    }
}*/
