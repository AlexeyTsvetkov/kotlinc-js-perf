@file:Suppress("HasPlatformType")

import org.gradle.api.internal.ConventionTask

plugins {
    kotlin("jvm") version "1.2.10"
    idea
}

repositories {
    jcenter()
    mavenCentral()
}

val kotlincForProfiling by configurations.creating

dependencies {
    compile(kotlin("stdlib"))
    kotlincForProfiling(kotlin("compiler", "1.2.10"))
}

val sourceSets = the<JavaPluginConvention>().sourceSets

sourceSets {
    getByName("main").java.srcDirs.clear()
    getByName("test").java.srcDirs.clear()
}
val generatorSourceSet = sourceSets.create("generator") {
    java.srcDirs("generator")
    compileClasspath += configurations.compile
    runtimeClasspath += configurations.compile
}

val generatorClasses by tasks

val generatedSourcesDir = File(project.buildDir, "generatedSrc").apply { mkdirs() }
val generate by tasks.creating(JavaExec::class.java) {
    dependsOn(generatorClasses)
    main = "MainKt"
    args = listOf(generatedSourcesDir.canonicalPath)
    classpath = generatorSourceSet.runtimeClasspath

    doFirst {
        generatedSourcesDir.deleteRecursively()
        generatedSourcesDir.mkdirs()
    }
}