pipeline {
    agent none
    stages {
        stage('Build Jar') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                    label 'docker-slave'
                }

            }
            steps {
                sh 'mvn clean package -DskipTests'
                //bat "mvn clean package -DskipTests"
            }
        }
        stage('Build Image') {
            steps {
                //bat "docker build -t='hendryang91/selenium-docker' ."
                script {
                	app = docker.build("hendryang91/selenium-docker")
                }
            }
        }
        stage('Push Image') {
            steps {
                /* withCredentials([usernamePassword(credentialsId: 'Dockerhub_creds', passwordVariable: 'pass' , usernameVariable: 'user']) {
                        bat 'docker login --username=${user} --password=${pass}'
                        bat 'docker push hendryang91/selenium-docker:latest'
                    }
                */
                script {
			        docker.withRegistry('https://registry.hub.docker.com', 'Dockerhub_creds') {
			        	app.push("${BUILD_NUMBER}")
			            app.push("latest")
			        }
                }
            }
        }
    }
}