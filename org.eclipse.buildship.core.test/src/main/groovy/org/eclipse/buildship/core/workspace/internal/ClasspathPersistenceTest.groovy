package org.eclipse.buildship.core.workspace.internal

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.JavaCore;

import org.eclipse.buildship.core.test.fixtures.ProjectImportSpecification
import org.eclipse.buildship.core.test.fixtures.TestEnvironment;
import org.eclipse.buildship.core.workspace.WorkspaceGradleOperations;

class ClasspathPersistenceTest extends ProjectImportSpecification {

    def "the classpath container is persisted"() {
        setup:
        file('sample-project', 'build.gradle') << '''apply plugin: "java"
           repositories { jcenter() }
           dependencies { compile "org.springframework:spring-beans:1.2.8"}
        '''
        executeProjectImportAndWait(folder('sample-project'))

        WorkspaceGradleOperations workspaceOperations = Mock(WorkspaceGradleOperations)
        TestEnvironment.registerService(WorkspaceGradleOperations, workspaceOperations)

        IJavaProject javaProject = JavaCore.create(findProject("sample-project"))
        IProject project = javaProject.project

        expect:
        javaProject.getResolvedClasspath(false).find { it.path.toPortableString().endsWith('spring-beans-1.2.8.jar') }

        when:
        reimportWithoutSynchronization(project)

        then:
        0 * workspaceOperations.synchronizeGradleBuildWithWorkspace(*_)
        javaProject.getResolvedClasspath(false).find { it.path.toPortableString().endsWith('spring-beans-1.2.8.jar') }

        cleanup:
        TestEnvironment.cleanup()
    }

    private static reimportWithoutSynchronization(IProject project) {
        def descriptor = project.description
        project.delete(false, true, null)
        project.create(descriptor, null)
        project.open(null)
        waitForJobsToFinish()
    }

}
