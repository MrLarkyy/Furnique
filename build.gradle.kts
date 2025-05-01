plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "9.0.0-beta11"
    id("co.uzzu.dotenv.gradle") version "2.0.0"
}

val projectVersion = "0.0.1-Beta"
group = "gg.aquatic.furnique"
version = projectVersion

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
}
kotlin {
    jvmToolchain(21)
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "com.gradleup.shadow")

    version = projectVersion

    repositories {
        mavenCentral()
        mavenLocal()
        maven(url = "https://mvn.lumine.io/repository/maven-public/")
        //maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://maven.radsteve.net/public")
        maven {
            url = uri("https://repo.nekroplex.com/releases")
        }
        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }


        maven {
            url = uri("https://repo.codemc.io/repository/maven-releases/")
        }
        maven {
            url = uri("https://repo.codemc.io/repository/maven-snapshots/")
        }
    }

    dependencies {
        implementation("net.radstevee.packed:packed-core:1.1.1")
        compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
        compileOnly("com.github.LoneDev6:API-ItemsAdder:3.6.2-beta-r3-b")
        compileOnly("gg.aquatic.comet:Comet-API:1.4.0")
        compileOnly ("com.ticxo.modelengine:ModelEngine:R4.0.8")
        compileOnly("gg.aquatic.waves:Waves:1.2.6:publish")
        compileOnly("org.joml:joml:1.10.8")
        implementation("me.rochblondiaux:blockbenchmodelreader:1.2")
        implementation("org.bstats:bstats-bukkit:3.0.2")

        compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
        //implementation("net.kyori:adventure-api:4.17.0")
    }

    kotlin {
        jvmToolchain(21)
    }
}