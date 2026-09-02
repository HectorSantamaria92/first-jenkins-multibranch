pipeline{
    agent any
    tools{
        maven 'Maven 3.9'
    }
    environment{
        IMAGE_NAME="hectorsantamaria92/multibranchjenkins"
        IMAGE_TAG="${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
    }
    stages{
        stage('Checkout'){
            steps{
                checkout scm
            }
        }
        stage('Build'){
            steps{
                sh 'mvn clean compile'
            }
        }
        stage('Test'){
            steps{
                sh 'mvn test'
            }
        }
        stage('Package'){
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Build y push Docker image'){
            steps{
                script{
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials'){
                        def image=docker.build("${IMAGE_NAME}:${IMAGE_TAG}")
                        image.push()
                    }
                }
            }
        }
    }
    post{
        success{
            echo "Pipeline exitoso en rama ${env.BRANCH_NAME}"
        }
        failure{
            echo "Pipeline  fallo en rama ${evn.BRANCH_NAME}"
        }
    }
}