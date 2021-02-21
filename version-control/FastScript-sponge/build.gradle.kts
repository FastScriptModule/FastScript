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

    compileOnly("org.spongepowered:spongeapi:7.3.0")
    compileOnly("com.github.rojo8399:PlaceholderAPI:master-SNAPSHOT")
    implementation("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT")

    implementation("org.bstats:bstats-sponge:1.8")
    implementation("com.iroselle:cstats-sponge:1.7")

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
        include(dependency("org.bstats:bstats-sponge:1.8"))
        include(dependency("com.iroselle:cstats-sponge:1.7"))

        include(dependency("me.scoretwo:commons-sponge-plugin:${rootProject.extra.get("commonsVersion")}"))
    }

    classifier = null
}
