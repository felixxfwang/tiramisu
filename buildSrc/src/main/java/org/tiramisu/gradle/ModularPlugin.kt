package org.tiramisu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ModularPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.apply(mutableMapOf("plugin" to "kotlin-kapt"))
        project.dependencies.add("kapt", "com.google.auto.service:auto-service:1.0-rc4")
        project.dependencies.add("annotationProcessor", "com.google.auto.service:auto-service:1.0-rc4")
        project.dependencies.add("implementation", "com.google.auto.service:auto-service:1.0-rc4")


//        kapt {
//            arguments {
//                arg("AROUTER_MODULE_NAME", project.name)
//            }
//        }

        project.dependencies.add("api", "com.alibaba:arouter-api:1.5.0")
        project.dependencies.add("kapt", "com.alibaba:arouter-compiler:1.2.2")
    }
}
