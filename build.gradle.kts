plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "de.derioo"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.jetbrains:annotations:24.1.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "de.derioo.Sodoku"
    }
}
