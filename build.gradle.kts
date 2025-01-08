plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven {
        url = uri("https://maven.pkg.github.com/KartoffelChipss/LifeStealZ")
        credentials {
            username = findProperty("GITHUB_USERNAME") as String? ?: ""
            password = findProperty("GITHUB_TOKEN") as String? ?: ""
        }
    }
    flatDir { dirs("libs") }
}

java {
    disableAutoTargetJvm()
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.lombok)
    compileOnly(libs.lifestealz)
    annotationProcessor(libs.lombok)
    compileOnly(fileTree("libs") { include("ExcellentCrates.jar") })
    compileOnly(fileTree("libs") { include("nightcore.jar") })
}

group = "me.gregzee.moderndupe"
version = "1.0.1"
description = "Plugin Description"

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveFileName = "${rootProject.name}-${project.version}.jar"
        archiveClassifier = null

        relocate("su.nightexpress.crates", "me.gregzee.moderndupe.libs.crates")
        relocate("su.nightexpress.nightcore", "me.gregzee.moderndupe.libs.nightcore")
        manifest {
            attributes["Implementation-Version"] = rootProject.version
        }
    }

    assemble {
        dependsOn(shadowJar)
    }

    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release = 17
    }

    withType<Javadoc>() {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        inputs.property("version", project.version)

        filesMatching("plugin.yml") {
            expand(
                "version" to rootProject.version,
            )
        }
    }

    defaultTasks("build")

    // 1.8.8 - 1.16.5 = Java 8
    // 1.17           = Java 16
    // 1.18 - 1.20.4  = Java 17
    // 1-20.5+        = Java 21
    val version = "1.20.4"
    val javaVersion = JavaLanguageVersion.of(21)

    val jvmArgsExternal = listOf(
        "-Dcom.mojang.eula.agree=true"
    )

    runServer {
        minecraftVersion(version)
        runDirectory = rootDir.resolve("run/paper/$version")

        javaLauncher = project.javaToolchains.launcherFor {
            languageVersion = javaVersion
        }

        downloadPlugins {
            url("https://github.com/EssentialsX/Essentials/releases/download/2.20.1/EssentialsX-2.20.1.jar")
        }

        jvmArgs = jvmArgsExternal
    }
}
