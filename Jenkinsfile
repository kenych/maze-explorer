pipeline {
    agent any

    tools {
/**      Uncomment if want to have specific java versions installed, otherwise maven tool will use jenkins default embedded java 8
 *       you will also need to uncomment java related stuff in java.groovy from dockerize jenkins project and make sure you have these java versions
 *       in your download folder
 */
//        jdk 'jdk8'
        maven 'maven3'
    }

    stages {
        stage('install and sonar parallel') {
            steps {
                parallel(
                        install: {
                            sh "mvn -U clean test cobertura:cobertura -Dcobertura.report.format=xml"
                        },
                        sonar: {
                            sh "mvn sonar:sonar"
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
        stage ('deploy'){
            steps{
                configFileProvider([configFile(fileId: 'our_settings', variable: 'SETTINGS')]) {
                    sh "mvn -s $SETTINGS deploy -DskipTests"
                }
            }
        }
    }
}
