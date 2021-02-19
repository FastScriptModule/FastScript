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
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common"))

        include(dependency("net.md-5:bungeecord-chat:1.16-R0.4-SNAPSHOT"))
        include(dependency("me.scoretwo:commons-velocity-plugin:${rootProject.extra.get("commonsVersion")}"))
    }

    classifier = null
}