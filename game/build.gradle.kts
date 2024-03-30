plugins {
    id("java")
    id("application")
}

group = "com.lukullu"
version = "1.0-SNAPSHOT"

//repositories {
//    mavenCentral()
//}

dependencies {
    implementation(fileTree("lib"))
    implementation(project(":common"))
}

application {
    mainClass = "com.lukullu.Main"
}
