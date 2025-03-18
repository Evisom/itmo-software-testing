plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.10.0"))  // Использование BOM для JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter")         // Основная зависимость для JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-params")  // Для параметрических тестов

    // Mockito для JUnit 5
    testImplementation("org.mockito:mockito-core:5.16.1")  // Основная зависимость для Mockito
    testImplementation("org.mockito:mockito-junit-jupiter:5.16.1")  // Для использования с JUnit 5
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
//    reports.html.required.set(true)
//    finalizedBy("copyTestReport")
}

//tasks.register<Copy>("copyTestReport") {
//    dependsOn("test")
//    from("$buildDir/reports/tests/test")
//    into("docs")
//}
