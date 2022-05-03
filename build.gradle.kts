plugins {
    id("org.jetbrains.intellij") version "1.3.1"
    java
    idea
}

group = "cn.mrdear.intellij"
version = "0.0.10"

dependencies {
    // https://mvnrepository.com/artifact/org.benf/cfr
    implementation("org.benf:cfr:0.152")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.2")
    plugins.addAll("java")
}
tasks {
    patchPluginXml {
        sinceBuild.set("221.2")
        changeNotes.set( """
        0.0.10:<br/>
        1. upgrade gradle to gradle kts <br/>
        2. upgrade cfr version to 152 <br/>
        0.0.9:<br/>
        1. upgrade cfr version <br/>
        0.0.8:<br/>
        1. support intellij 2020.3 <br/>
        """.trimIndent())
    }
}
