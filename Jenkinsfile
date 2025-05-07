pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('1')
        DOCKER_IMAGE = 'tmq244/teedy'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }
    stages {
        stage('Build') {
            steps {
                bat 'mvn -B -DskipTests clean package'
            }
        }
        stage('Verify Docker') {
            steps {
                bat 'wsl docker --version'
                bat 'wsl docker info --format "{{.ServerVersion}}"'
            }
        }
        stage('Building image') {
            steps {
                script {
                    bat "wsl docker build -t ${env.DOCKER_IMAGE}:${env.DOCKER_TAG} ."
                }
            }
        }
        stage('Upload image') {
            steps {
                script {
                    bat "wsl docker login -u %DOCKER_HUB_CREDENTIALS_USR% -p %DOCKER_HUB_CREDENTIALS_PSW%"
                    bat "wsl docker push ${env.DOCKER_IMAGE}:${env.DOCKER_TAG}"
                    bat "wsl docker tag ${env.DOCKER_IMAGE}:${env.DOCKER_TAG} ${env.DOCKER_IMAGE}:latest"
                    bat "wsl docker push ${env.DOCKER_IMAGE}:latest"
                }
            }
        }
        stage('Run containers') {
            steps {
                script {
                    bat "wsl docker stop teedy-container-8081 || exit 0"
                    bat "wsl docker rm teedy-container-8081 || exit 0"
                    bat "wsl docker run --name teedy-container-8081 -d -p 8081:8080 ${env.DOCKER_IMAGE}:${env.DOCKER_TAG}"
                    bat "wsl docker ps --filter \"name=teedy-container\""
                }
            }
        }
    }
}