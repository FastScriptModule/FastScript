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
    implementation("me.scoretwo:commons-bungee-plugin:${rootProject.extra.get("commonsVersion")}")

    implementation("org.bstats:bstats-bungeecord:1.8")
    implementation("com.iroselle:cstats-bungeecord:1.7")

    compileOnly("net.md-5:bungeecord-api:1.16-R0.4-SNAPSHOT")
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
        include(dependency("org.bstats:bstats-bungeecord:1.8"))
        include(dependency("com.iroselle:cstats-bungeecord:1.7"))

        include(dependency("me.scoretwo:commons-bungee-plugin:${rootProject.extra.get("commonsVersion")}"))
    }

    classifier = null
}
