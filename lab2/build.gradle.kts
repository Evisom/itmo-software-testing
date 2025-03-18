plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
    reports.html.required.set(true)
    finalizedBy("copyTestReport")
}


tasks.register<Copy>("copyTestReport") {
    dependsOn("test")
    from("$buildDir/reports/tests/test")
    into("docs")
}
