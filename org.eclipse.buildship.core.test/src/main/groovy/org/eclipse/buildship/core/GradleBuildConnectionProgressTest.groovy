package org.eclipse.buildship.core

import java.util.function.Function

import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.model.GradleProject

import org.eclipse.buildship.core.internal.test.fixtures.ProjectSynchronizationSpecification

class GradleBuildConnectionProgressTest extends ProjectSynchronizationSpecification {

    def "Null monitor can be used when no progress is desired"() {
       setup:
       File location = dir('GradleBuildConnectionProgressTest')

       when:
       GradleBuild gradleBuild = gradleBuildFor(location)
       Function query = { ProjectConnection c -> c.model(GradleProject).get() }
       GradleProject model = gradleBuild.withConnection(query, null)

       then:
       model
    }
}
