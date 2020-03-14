package org.tiramisu.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ModularPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.apply plugin: 'kotlin-kapt'
        project.dependencies {
            kapt "com.google.auto.service:auto-service:1.0-rc4"
            annotationProcessor "com.google.auto.service:auto-service:1.0-rc4"
            implementation "com.google.auto.service:auto-service:1.0-rc4"

            implementation "com.alibaba:arouter-api:1.5.0"
            kapt "com.alibaba:arouter-compiler:1.2.2"
        }

        project.kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }

        if (isAndroidProject(project)) {
            copyARouterProFile(project)
            project.android.buildTypes.release {
                proguardFile getTargetARouterProFile(project)
            }
        }
    }

    private def copyARouterProFile(Project project) {
        def proFile = getTargetARouterProFile(project)
        if (!proFile.exists()) {
            proFile.parentFile.mkdirs()
            def inputStream = this.class.classLoader.getResourceAsStream('arouter-rules.pro')
            def outputStream = new FileOutputStream(proFile)
            IOUtils.copy(inputStream, outputStream)
            IOUtils.closeQuietly(outputStream)
            IOUtils.closeQuietly(inputStream)
        }
    }

    private static def getTargetARouterProFile(Project project) {
        return new File(project.rootProject.buildDir, "modular/arouter-rules.pro")
    }

    private static boolean isAndroidProject(Project project) {
        return project.plugins.hasPlugin('com.android.application') || project.plugins.hasPlugin('com.android.library')
    }
}
