group = "gg.aquatic.furnique.plugin"
version = parent!!.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":API"))
}

tasks {

    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand(getProperties())
            expand(mutableMapOf("version" to parent!!.version))
        }
    }

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    artifacts {
        archives(sourcesJar)
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("Furnique-${project.version}.jar")
    archiveClassifier.set("all")

    relocate("org.bstats", "gg.aquatic.furnique.shadow.bstats")
    exclude("kotlin/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
    relocate("kotlin", "gg.aquatic.waves.shadow.kotlin")
}