pipeline {
    agent any

    tools {
        maven 'maven3'
    }

    stages {
        stage('test') {
            steps {
                sh "mvn -U clean test cobertura:cobertura -Dcobertura.report.format=xml"
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
