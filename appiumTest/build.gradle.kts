plugins {
    application
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("io.appium:java-client:8.5.1")
    implementation("org.seleniumhq.selenium:selenium-java:4.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}
