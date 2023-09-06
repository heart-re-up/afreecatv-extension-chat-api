group = "com.tinteccnc"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":util-logger"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    implementation("com.bucket4j:bucket4j-core:8.2.0")
}
