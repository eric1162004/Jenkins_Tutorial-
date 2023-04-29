pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS') // Global timeout
        retry(3) // Global retry
    }
    environment {
        CC = 'clang'
    }
    post {
        always {
            echo 'I will always say Hello again!'
        }
    }
    stages {
        stage('Build') {
            options {
                timeout(time: 20, unit: 'MINUTES') // Stage timeout
                retry(3) // Stage retry
            }
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    echo 'Building'
                    echo "print env ${env.CC} or $CC"
                }
                sh'''
                    echo 'Multiline shell steps'
                    ls -l
                '''
                withEnv(['PATH+EXTRA=/usr/apache/bin']){
                    // sh 'mvn install'
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing'
                // sh 'exit 1'
                script {
                    // include script block
                    def name = "DevOps"
                    if (name == "DevOps") {
                        echo "it's deveops"
                    } else {
                        echo "it's not deveops"
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying'
            }
        }
    }
}
