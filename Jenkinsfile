pipeline {
    agent any

    tools {
        maven 'maven'   // remplace par le nom EXACT vu dans Global Tool Configuration
    }

    parameters {
        choice(name: 'ENVIRONNEMENT', choices: ['dev', 'recette', 'prod'], description: 'Environnement cible')
        string(name: 'VERSION', defaultValue: '1.0.0', description: 'Version de l\'artefact')
    }

    environment {
        // Nom déclaré dans Manage Jenkins -> System -> SonarQube servers
        SONARQUBE_ENV = 'sonarQube'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/thierryDEMBABA/HW-maven.git'
            }
        }

        stage('Info build') {
            steps {
                echo "Build pour l'environnement : ${params.ENVIRONNEMENT}"
                echo "Version de l'artefact : ${params.VERSION}"
                echo "Nom du job : ${env.JOB_NAME}"
                echo "Numéro du build : ${env.BUILD_NUMBER}"
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }

        stage('Deploy Tomcat') {
            steps {
                deploy adapters: [tomcat9(
                    credentialsId: 'tomcat-deploy', // remplace par l'ID exact de ton credential
                    url: 'http://tomcat:8080'
                )],
                contextPath: 'hello-world',
                war: 'target/*.war'
            }
        }
    }

    post {
        success {
            echo "Pipeline terminé avec succès pour ${params.ENVIRONNEMENT} - version ${params.VERSION}"
        }
        failure {
            echo "Le pipeline a échoué."
        }
    }
}