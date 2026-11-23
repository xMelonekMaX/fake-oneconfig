@file:Suppress("UnstableApiUsage")

import dev.kikugie.loomx.LoomCompatDependencyExtension
import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension

plugins {
    java
    kotlin("jvm")
    id("dev.kikugie.loom-back-compat")
}

val stonecutter = extensions.getByName("stonecutter") as StonecutterBuildExtension
val mcVersion = stonecutter.current.version

run {
    val (version, loader) = stonecutter.current.project.split("-", limit = 2)
    stonecutter.properties.tags(version, loader)
}

fun optionalProperty(name: String): String? = findProperty(name)?.toString()?.takeIf { it.isNotBlank() }

val minecraftPredicate = if (mcVersion.startsWith("26.")) "~$mcVersion" else mcVersion
val modId = property("mod.id") as String
val modVersion = property("mod.version") as String
val fabricLoaderVersion = property("deps.fabric_loader") as String
val fabricLanguageKotlinVersion = property("deps.fabric_language_kotlin") as String

group = property("mod.group") as String
version = "${stonecutter.current.project}-$modVersion"
base.archivesName = (property("mod.name") as String).replace(' ', '-')

repositories {
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }

    mavenCentral()
    google()
    maven("https://redirector.kotlinlang.org/maven/compose-dev")
    maven("https://jitpack.io") {
        content { includeGroupAndSubgroups("com.github") }
    }
    maven("https://maven.fabricmc.net/") {
        content { includeGroupAndSubgroups("net.fabricmc") }
    }
    maven("https://central.sonatype.com/repository/maven-snapshots") {
        content { includeGroup("net.kyori") }
    }
    strictMaven("https://maven.terraformersmc.com/releases/", "TerraformersMC", "com.terraformersmc")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    strictMaven("https://maven.bawnorton.com/releases", "Bawnorton", "com.github.bawnorton.mixinsquared")
    mavenLocal()
}

val javaVersion = if (stonecutter.current.parsed >= "26.1") 25 else 21

configure<KotlinJvmExtension> {
    jvmToolchain(javaVersion)
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}


dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    dependencies.extensions.getByType<LoomCompatDependencyExtension>().applyMojangMappings()

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${property("deps.kotlin")}")
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
    runtimeOnly("net.fabricmc:fabric-language-kotlin:$fabricLanguageKotlinVersion")
}

tasks.withType<ProcessResources>().configureEach {
    val modName = project.property("mod.name") as String
    inputs.property("modId", modId)
    inputs.property("modName", modName)
    inputs.property("modVersion", modVersion)
    inputs.property("minorMcVersion", minecraftPredicate)
    filesMatching("fabric.mod.json") {
        expand(
            mapOf(
                "mod_id" to modId,
                "mod_name" to modName,
                "mod_version" to modVersion,
                "mod_description" to "Fake OneConfig",
                "minor_mc_version" to minecraftPredicate,
            ),
        )
    }
}

tasks.matching { it.name == "createMinecraftArtifacts" }.configureEach {
    dependsOn("stonecutterGenerate")
}
