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
    		git 'https://github.com/lovelinuxalot/maze-explorer.git'
  	}
	
        stage('Build and test') {
        	buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean package cobertura:cobertura -Dcobertura.report.format=xml'
	}
	
	stage('Unit Test') {
        	junit '**/target/*-reports/TEST-*.xml'
                step([$class: 'CoberturaPublisher', coberturaReportFile: 'target/site/cobertura/coverage.xml'])
	}
	
	
	//stage('SonarQube analysis') {
    		// requires SonarQube Scanner 2.8+
	//	def mvnHome = tool name: 'maven3', type: 'maven'
    	//	withSonarQubeEnv('sonar') {
	//		sh "${mvnHome}/bin/mvn sonar:sonar"
    	//	}
  	//}
	
	stage('SonarQube analysis'){
		def scannerHome = tool 'scanner'
                withSonarQubeEnv('sonar') {
                    sh "${scannerHome}/bin/sonar-scanner"
		}
	}

        stage('Publish build info') {
            server.publishBuildInfo buildInfo
        }
}
