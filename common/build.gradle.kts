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

    implementation("org.jetbrains.kotlin:kotlin-script-util:${rootProject.extra.get("kotlinVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-compiler:${rootProject.extra.get("kotlinVersion")}")

    implementation("commons-lang:commons-lang:2.6")
    implementation("commons-io:commons-io:2.7")
    implementation("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-command:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}")
    implementation("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common"))

        exclude(dependency("commons-io:commons-io:2.7"))
        include(dependency("commons-lang:commons-lang:2.6"))

        include(dependency("me.scoretwo:commons-syntaxes:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-command:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-server:${rootProject.extra.get("commonsVersion")}"))
        include(dependency("me.scoretwo:commons-bukkit-configuration:${rootProject.extra.get("commonsVersion")}"))
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