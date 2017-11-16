
podTemplate(label: 'test', containers: [
  containerTemplate(name: 'maven', image: 'maven:3.2-jdk-7-onbuild', ttyEnabled: true, command: 'cat')
  ]) {

  node('test') {
    stage('test') {
      container('maven') {
          sh 'mvn --version'
      }
    }
  }
}