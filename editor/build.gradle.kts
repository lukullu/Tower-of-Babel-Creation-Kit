plugins {
    id("java")
    id("application")
}

group = "com.kilix"
version = "1.0-SNAPSHOT"

application {
    mainClass = "com.kilix.tbck.editor.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree("lib"))
    implementation(project(":common"))

    implementation("com.formdev:flatlaf:3.4.1")
}

