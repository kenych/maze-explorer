pipeline {
    agent any

    tools {
        maven 'maven3'
    }

    stages {
        stage('test') {
            steps {
		//use shared library
		mavenTest()
            }
            post {
                always {
                    junit '**/target/*-reports/TEST-*.xml'
                    step([$class: 'CoberturaPublisher', coberturaReportFile: 'target/site/cobertura/coverage.xml'])
                }
            }
        }
    }
}
