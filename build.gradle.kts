plugins {
    val kotlinVersion = "1.8.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.14.0"
}

group = "org.example"
version = "0.1.0"

dependencies {
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    // https://mvnrepository.com/artifact/io.ktor/ktor-serialization-kotlinx-json
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")
    // https://mvnrepository.com/artifact/org.dom4j/dom4j
    implementation("org.dom4j:dom4j:2.1.4")
}

repositories {
    if (System.getenv("CI")?.toBoolean() != true) {
        maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    }
    mavenCentral()
}
