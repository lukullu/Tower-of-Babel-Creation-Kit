plugins {
    id("java")
    id("application")
}

group = "com.kilix"
version = "1.0-SNAPSHOT"

application {
    mainClass = "com.kilix.tobck.editor.Main"
}

dependencies {
    implementation(fileTree("lib"))
    implementation(project(":common"))
}
