plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
    id("net.kyori.blossom")
}

blossom {
    replaceTokenIn("src/main/kotlin/me/scoretwo/fastscript/sponge/SpongeBootStrap.kt")
    replaceToken("%%id%%", rootProject.name.toLowerCase())
    replaceToken("%%name%%", rootProject.name)
    replaceToken("%%version%%", project.version)
    replaceToken("%%description%%", project.description)
}

dependencies {
    implementation(project(":FastScript-common"))

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

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common"))

        include(dependency("me.scoretwo:commons-sponge-plugin:${rootProject.extra.get("commonsVersion")}"))
    }

    classifier = null
}
