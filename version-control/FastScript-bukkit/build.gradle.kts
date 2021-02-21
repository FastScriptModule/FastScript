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

    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.9")

    implementation("org.bstats:bstats-bukkit:1.8")
    implementation("com.iroselle:cstats-bukkit:1.7")

    implementation("me.scoretwo:commons-bukkit-plugin:${rootProject.extra.get("commonsVersion")}")
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

        include(dependency("org.bstats:bstats-bukkit:1.8"))
        include(dependency("com.iroselle:cstats-bukkit:1.7"))
        include(dependency("me.scoretwo:commons-bukkit-plugin:${rootProject.extra.get("commonsVersion")}"))
    }
    relocate("org.bstats","me.scoretwo.utils.shaded.org.bstats")
    relocate("com.iroselle.cstats","me.scoretwo.utils.shaded.com.iroselle.cstats")

    classifier = null
}
