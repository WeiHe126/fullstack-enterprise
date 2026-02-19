pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }
    
    tools {
        maven 'Maven3'
    }

    environment {
        SONARQUBE_ENV = 'sonar'
    }

    stages {
        stage('Checkout') {
            steps {
                deleteDir() // borra todo el workspace
                checkout([$class: 'GitSCM',
                        branches: [[name: '*/master']],
                        userRemoteConfigs: [[url: 'https://github.com/WeiHe126/fullstack-enterprise']]])
            }
        }

        stage('Build & Compile') {
            steps {
                dir('backend') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Unit & Integration Tests') {
            steps {
                dir('backend') {
                    sh 'mvn verify'
                }
            }
        }

        stage('Security Scan') {
            steps {
                dir('backend') {
                    sh 'mvn dependency-check:check'
                }
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_HOST_URL = 'http://sonarqube:9000'
            }
            steps {
                dir('backend') {
                    withSonarQubeEnv('sonar') {
                        sh """
                        mvn sonar:sonar \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml,target/site/jacoco-it/jacoco.xml \
                            -Dsonar.coverage.exclusions=**/model/**,**/exception/**,**/BackendApplication.java
                        """
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('backend') {
                    sh 'docker build -t enterprise-backend:latest .'
                }
            }
        }

        stage('Deploy (Simulado)') {
            steps {
                sh 'docker run -d -p 8080:8080 enterprise-backend:latest'
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado correctamente ✅'
        }
        failure {
            echo 'Pipeline falló ❌'
        }
    }
}
