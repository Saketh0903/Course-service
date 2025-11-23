pipeline {
    agent any
    environment {
        APP_NAME = "courseservice"
        APP_NAMESPACE = "${APP_NAME}-ns"
        IMAGE_NAME = "${APP_NAME}-image"
        IMAGE_TAG = "latest"
        APP_PORT = 8100
        NODE_PORT = 30081
        REPLICA_COUNT = 2
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Saketh0903/Course-service.git'
            }
        }
        stage('Build') {
            steps {
                // Build docker image using Dockerfile
                bat "docker build -t ${IMAGE_NAME}:${IMAGE_TArG} -f Dockerfile ."
            }
        }
        /*
        stage('Run Container') {
            steps {
                script {
                    // Run docker container instance
                    bat "docker run -d --name ${APP_NAME} -p 8100:8100 ${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
        */
        stage('K8s Container Deployment') {
            steps {
                script {
                    withEnv(["KUBECONFIG=C:\\Users\\user\\.kube\\config"]) {
                        // Apply manifests
                        bat "kubectl apply -f namespace.yaml --validate=false"
                        bat "kubectl apply -f deployment.yaml --validate=false"
                        bat "kubectl apply -f service.yaml --validate=false"
                    }
                }
            }
        }
    }
    post {
        success {
            echo "✅ Checkout, Build, Dockerize & Deploy completed successfully!"
        }
        failure {
            echo "❌ Build failed!"
        }
    }
}
