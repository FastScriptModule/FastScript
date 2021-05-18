![](https://img.iroselle.com/plugins/fastscript/big_logo.gif)
[<img src="https://img.shields.io/discord/760802420552105995?label=Discord&style=for-the-badge"/>](https://discord.gg/GVM6vx9)
[<img src="https://img.shields.io/github/issues/Score2/FastScript?style=for-the-badge"/>](https://github.com/Score2/FastScript/issues)
[<img src="https://img.shields.io/github/issues-pr/Score2/FastScript?style=for-the-badge"/>](https://github.com/Score2/FastScript/pulls)
[<img src="https://img.shields.io/github/license/Score2/FastScript?style=for-the-badge"/>](https://github.com/Score2/FastScript/blob/master/LICENSE)
[<img src="https://img.shields.io/github/last-commit/Score2/FastScript?style=for-the-badge"/>](https://github.com/Score2/FastScript/commits/master)
[<img src="https://img.shields.io/bstats/servers/9014?label=BSTATS-SERVERS&style=for-the-badge"/>](https://bstats.org/plugin/bukkit/FastScript/9014)
> Because the structure of the project is too confusing, the project will stop updating until major errors are discovered. Reset the project when [Insinuate](https://github.com/InsinuateProjects/Insinuate) completes development and testing.
***
### About
Support multiple scripting languages, use scripts to change Minecraft!

>At present, the project is still unfinished and cannot be used.
***
### Build
In the project root directory:
```
./gradle shadowJar
  or
./gradle
```
***
### Developer
> Maven usage
```
<repositories>
    <repository>
        <id>roselle-repo</id>
        <url>http://repo.iroselle.com/snapshots/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>me.scoretwo</groupId>
        <artifactId>FastScript</artifactId>
        <version>1.0.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
> Gradle
```
repositories {
    maven {url 'http://repo.iroselle.com/snapshots/'}
}
dependencies {
    implementation 'me.scoretwo:FastScript:1.0.1-SNAPSHOT'
}
```
> Gradle Kotlin DSL
```
repositories {
    maven("http://repo.iroselle.com/snapshots/")
}
dependencies {
    implementation("me.scoretwo:FastScript-common:1.0.1-SNAPSHOT")
}
```

### Links

[<img src="http://mc3.roselle.vip:602/icons/github.svg" width="64" height="64"/>](https://github.com/Score2/FastScript) 　
[<img src="http://mc3.roselle.vip:602/icons/wiki.svg" width="64" height="64"/>](https://github.com/Score2/FastScript/wiki) 　
[<img src="http://mc3.roselle.vip:602/icons/discord.svg" width="64" height="64"/>](https://discord.gg/GVM6vx9)
