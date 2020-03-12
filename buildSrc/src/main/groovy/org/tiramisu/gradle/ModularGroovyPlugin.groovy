package org.tiramisu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ModularGroovyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.apply plugin: 'kotlin-kapt'
        project.dependencies {
            kapt "com.google.auto.service:auto-service:1.0-rc4"
            annotationProcessor "com.google.auto.service:auto-service:1.0-rc4"
            implementation "com.google.auto.service:auto-service:1.0-rc4"

            api "com.alibaba:arouter-api:1.5.0"
            kapt "com.alibaba:arouter-compiler:1.2.2"
        }

        project.kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }

    }
}
