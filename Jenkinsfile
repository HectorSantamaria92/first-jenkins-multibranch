pipeline{
    agent any
    tools{
        maven 'Maven 3.9'
    }
    environment{
        IMAGE_NAME="hectorsantamaria92/multibranchjenkins"
        IMAGE_TAG="${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
        EC2_HOST   = "13.218.53.28"

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
        stage('Deployando a AWS EC2'){
            when{
                branch 'dev'
            }
            steps{
                sshagent(credentials:['ec2-ssh-key']){
                    sh  """
                        ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} '
                            docker pull hectorsantamaria92/multibranchjenkins:${IMAGE_TAG} &&
                            docker stop multibranch-app || true &&
                            docker rm multibranch-app || true &&
                            docker run -d -p 8082:8082 --name multibranch-app hectorsantamaria92/multibranchjenkins:${IMAGE_TAG}
                        '
                    """
                }
            }
        }
    }
    post{
        success{
            echo "Pipeline exitoso en rama ${env.BRANCH_NAME}"
        }
        failure{
            echo "Pipeline  fallo en rama ${env.BRANCH_NAME}"
        }
    }
}