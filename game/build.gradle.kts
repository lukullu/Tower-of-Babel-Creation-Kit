import java.nio.file.Files

plugins {
    id("java")
    id("application")
}

group = "com.lukullu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree("lib"))
    implementation(project(":common"))
}

application {
    mainClass = "com.lukullu.Main"
    val workingDir = File(rootProject.projectDir, "run")
    if (! workingDir.exists()) Files.createDirectories(workingDir.toPath())
    tasks.run.get().workingDir = workingDir
}
