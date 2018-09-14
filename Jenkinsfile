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

	stage('SCM') {
    		git 'https://github.com/jijeesh/maze-explorer.git'
  	}

		checkout()
		build()
		allTests()
		preview()
		sonarServer()
		allCodeQualityTests()
		preProduction()
		manualPromotion()
		production()
}
		def checkout() {

				stage('Checkout') {
				}
		}
		def build(){
			stage name: 'Build', concurrency: 1

		}
		def allTests(){
			stage name: 'allTests', concurrency: 1

		}
		def preview(){
			stage name: 'Preview', concurrency: 1

		}
		def sonarServer(){
			stage name: 'sonarServer', concurrency: 1

		}
		def allCodeQualityTests(){
			stage name: 'allCodeQualityTests', concurrency: 1

		}

		def BrowserTests(){
			stage('Chrome') {
			}
			stage('Firefox') {
			}
			stage('Ie-10') {
			}
			stage('Opera') {
			}

		}

		def preProduction(){
			stage name: 'preProduction', concurrency: 1

		}
		def manualPromotion(){
			stage name: 'manualPromotion', concurrency: 1

		}
		def production(){
			stage name: 'production', concurrency: 1
		}

  //       stage('Build and test') {
  //       	buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean package cobertura:cobertura -Dcobertura.report.format=xml'
	// }

	// stage('Unit Test') {
  //       	junit '**/target/*-reports/TEST-*.xml'
  //               step([$class: 'CoberturaPublisher', coberturaReportFile: 'target/site/cobertura/coverage.xml'])
	// }


	//stage('SonarQube analysis') {
    		// requires SonarQube Scanner 2.8+
	//	def mvnHome = tool name: 'maven3', type: 'maven'
    	//	withSonarQubeEnv('sonar') {
	//		sh "${mvnHome}/bin/mvn sonar:sonar"
    	//	}
  	//}

	// stage('SonarQube analysis'){
	// 	def scannerHome = tool 'scanner'
  //               withSonarQubeEnv('sonar') {
  //                   sh "${scannerHome}/bin/sonar-scanner"
	// 	}
	// }
	//
  //       stage('Publish build info') {
  //           server.publishBuildInfo buildInfo
  //       }
