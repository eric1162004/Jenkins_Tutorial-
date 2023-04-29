pipeline {
    agent any
    options {
        // can be at global or stage level
        buildDiscarder(logRotator(numToKeepStr: '1'))
        checkoutToSubdirectory('foo')
        disableConcurrentBuilds() // Disallow concurrent executions of the Pipeline
        quietPeriod(30) 
        timeout(time: 1, unit: 'HOURS')
    }
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')

        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')

        booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')

        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')

        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
    }
    tools {
        maven 'M3'
    }
    stages {
        stage('Clone Stage') {
            steps {
                git branch: 'main', url: 'https://github.com/eric1162004/spring-petclinic.git'
            }
        }
        stage('Build') {
            steps {
                // withEnv(["PATH+EXTRA=/var/jenkins_home/opt/apache-maven-3.9.1"]) {
                sh 'mvn install'
                // }
            }
        }
    }
}

