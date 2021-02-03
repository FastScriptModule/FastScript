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
    replaceTokenIn("src/main/kotlin/me/scoretwo/fastscript/velocity/VelocityBootStrap.kt")
    replaceToken("%%id%%", project.name.toLowerCase())
    replaceToken("%%name%%", project.name)
    replaceToken("%%version%%", project.version)
    replaceToken("%%description%%", project.description)
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

tasks.processResources {
    from("src/main/resource") {
        include("velocity-plugin.json")
        expand(mapOf(
            "id" to project.name.toLowerCase(),
            "name" to project.name,
            "version" to project.version,
            "main" to "${rootProject.group}.${rootProject.name.toLowerCase()}.velocity.VelocityBootStrap",
            "description" to project.description
        ))
    }
}