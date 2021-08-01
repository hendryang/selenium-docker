pipeline {
    agent none
    stages {
        stage('Build Jar') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                    label 'docker-slave-cloud'
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
            agent {label 'docker-slave-cloud'}
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
        stage ("Setup environment") {
            agent {label 'docker-slave-cloud'}
            steps {
                // sh "apk update && apk add py-pip python3-dev libffi-dev openssl-dev gcc libc-dev make"
                // sh "curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y" // install rust
                // sh "source $HOME/.cargo/env" // configure rust environment path
                // sh "pip install docker-compose"
                sh "docker-compose down"
                sh "docker-compose up --no-color -d selenium-hub chrome firefox"
            }
        }
        stage ("Execute Test") {
            agent {label 'docker-slave-cloud'}
            steps {
                sh "docker-compose up --no-color bookflight-module"
            }
        }
        stage ("Teardown environment") {
            agent {label 'docker-slave-cloud'}
            steps {
                sh "docker-compose down"
            }
        }
    }
}