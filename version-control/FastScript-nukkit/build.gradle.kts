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

    compileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")

    implementation("commons-lang:commons-lang:2.6")

    implementation("me.scoretwo:commons-nukkit-plugin:${rootProject.extra.get("commonsVersion")}")
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

        include(dependency("me.scoretwo:commons-bukkit-plugin:${rootProject.extra.get("commonsVersion")}"))
    }

    classifier = null
}
