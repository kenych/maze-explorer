node {
    	// Get Artifactory server instance, defined in the Artifactory Plugin administration page.
	def server = Artifactory.server "artifactory"
	// Create an Artifactory Maven instance.
	def rtMaven = Artifactory.newMavenBuild()
	def buildInfo    

        stage('Artifactory configuration') {
		    // Tool name from Jenkins configuration
		    rtMaven.tool = "maven3"
		    // Set Artifactory repositories for dependencies resolution and artifacts deployment.
		    rtMaven.deployer releaseRepo:'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server
		    rtMaven.resolver releaseRepo:'libs-release', snapshotRepo:'libs-snapshot', server: server
	    }
        
        stage('install and sonar parallel') {
            steps {
                parallel(
                        install: {
                            buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean test cobertura:cobertura -Dcobertura.report.format=xml'
                        },
                        sonar: {
                            sh "mvn sonar:sonar -Dsonar.host.url=${env.SONARQUBE_HOST}"
                        }
                )
            }
            post {
                always {
                    junit '**/target/*-reports/TEST-*.xml'
                    step([$class: 'CoberturaPublisher', coberturaReportFile: 'target/site/cobertura/coverage.xml'])
                }
            }
        }

        stage('Publish build info') {
            server.publishBuildInfo buildInfo
        }
}
