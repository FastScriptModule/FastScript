plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.github.johnrengelman.shadow")
    id("maven")
    id("maven-publish")
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.8.6")
    compileOnly("org.slf4j:slf4j-log4j12:1.7.30")
    compileOnly("net.md-5:bungeecord-chat:1.16-R0.5-SNAPSHOT")

    compileOnly("commons-lang:commons-lang:2.6")
    compileOnly("commons-io:commons-io:2.7")
    compileOnly("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}")
    compileOnly("org.jetbrains.kotlin:kotlin-script-util:${rootProject.extra.get("kotlinVersion")}")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler:${rootProject.extra.get("kotlinVersion")}")
    implementation("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common"))

        exclude(dependency("commons-io:commons-io:2.7"))
        exclude(dependency("commons-lang:commons-lang:2.6"))

        exclude(dependency("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}"))

        include(dependency("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}"))
    }
    relocate("org.apache","me.scoretwo.utils.libs.org.apache")

    classifier = null
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
        }
    }
}