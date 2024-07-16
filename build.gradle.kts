import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    id("java")
    id ("com.adarshr.test-logger") version("4.0.0")
}

group = "me.mertunctuncer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

testlogger {
    theme = ThemeType.PLAIN
    slowThreshold = 5000
}
tasks.test {
    outputs.upToDateWhen {false}
    useJUnitPlatform()
}