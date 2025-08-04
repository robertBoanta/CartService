pipeline {
    agent any

    environment {
        // Docker Hub credentials (using your existing credential ID)
        DOCKERHUB_CREDENTIALS = credentials('DOCKERHUB_PAT')
        DOCKER_IMAGE = 'near1715/cart-service'
        IMAGE_TAG = "${BUILD_NUMBER}"
        LATEST_TAG = 'latest'

        // GitHub credentials (using your existing credential ID)
        GITHUB_CREDENTIALS = credentials('cbe23c8d-51aa-4388-a14f-b81dfaea907e')

        // Kubernetes cluster credentials
        KUBECONFIG_CREDENTIAL = credentials('kubeconfig-credential')
        KUBERNETES_NAMESPACE = 'default'

        // Application configuration
        APP_NAME = 'cart-service'
        APP_PORT = '8082'
    }

    triggers {
        // Poll GitHub repository for changes every 2 minutes
        pollSCM('H/2 * * * *')

        // Alternative: Use GitHub webhook (recommended)
        // githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from GitHub...'
                // Use your GitHub credentials for checkout if needed for private repos
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/your-username/CartService.git',
                        credentialsId: 'cbe23c8d-51aa-4388-a14f-b81dfaea907e'
                    ]]
                ])

                script {
                    // Get commit info for build metadata
                    env.GIT_COMMIT_SHORT = sh(
                        script: 'git rev-parse --short HEAD',
                        returnStdout: true
                    ).trim()
                    env.GIT_BRANCH = sh(
                        script: 'git rev-parse --abbrev-ref HEAD',
                        returnStdout: true
                    ).trim()
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'chmod +x ./mvnw'
                sh './mvnw clean test'
            }
            post {
                always {
                    // Publish test results
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'

                    // Archive test results
                    archiveArtifacts artifacts: 'target/surefire-reports/*.xml', allowEmptyArchive: true
                }
            }
        }

        stage('Build JAR') {
            steps {
                echo 'Building Spring Boot JAR...'
                sh './mvnw clean package -DskipTests'
            }
            post {
                success {
                    // Archive the built JAR
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'

                    // Build image with multiple tags
                    def image = docker.build("${DOCKER_IMAGE}:${IMAGE_TAG}")

                    // Tag as latest
                    sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} ${DOCKER_IMAGE}:${LATEST_TAG}"

                    // Tag with git commit
                    sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} ${DOCKER_IMAGE}:${GIT_COMMIT_SHORT}"
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    echo 'Pushing image to Docker Hub...'

                    // Login to Docker Hub
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'

                    // Push all tags
                    sh "docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${DOCKER_IMAGE}:${LATEST_TAG}"
                    sh "docker push ${DOCKER_IMAGE}:${GIT_COMMIT_SHORT}"
                }
            }
            post {
                always {
                    // Logout from Docker Hub
                    sh 'docker logout'
                }
            }
        }

        stage('Update Kubernetes Manifests') {
            steps {
                script {
                    echo 'Updating Kubernetes deployment manifests...'

                    // Update the deployment.yaml with the new image tag
                    sh """
                        sed -i 's|image: cart-service:latest|image: ${DOCKER_IMAGE}:${IMAGE_TAG}|g' k8s/deployment.yaml

                        # Add build metadata as labels
                        sed -i '/metadata:/a\\    labels:\\n      build-number: "${BUILD_NUMBER}"\\n      git-commit: "${GIT_COMMIT_SHORT}"\\n      build-date: "$(date -u +%Y-%m-%dT%H:%M:%SZ)"' k8s/deployment.yaml
                    """

                    // Show the updated deployment file for verification
                    sh 'echo "Updated deployment.yaml:" && head -20 k8s/deployment.yaml'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    echo 'Deploying to Kubernetes cluster...'

                    // Set up kubectl with credentials
                    withCredentials([file(credentialsId: 'kubeconfig-credential', variable: 'KUBECONFIG_FILE')]) {
                        sh """
                            # Copy kubeconfig to workspace
                            cp \$KUBECONFIG_FILE ~/.kube/config
                            chmod 600 ~/.kube/config

                            # Verify connection to cluster
                            kubectl cluster-info
                            kubectl get nodes

                            # Create namespace if it doesn't exist
                            kubectl create namespace ${KUBERNETES_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -

                            # Apply the Kubernetes manifests
                            kubectl apply -f k8s/deployment.yaml -n ${KUBERNETES_NAMESPACE}
                            kubectl apply -f k8s/service.yaml -n ${KUBERNETES_NAMESPACE}

                            # Wait for deployment to complete
                            kubectl rollout status deployment/${APP_NAME} -n ${KUBERNETES_NAMESPACE} --timeout=300s

                            # Show deployment status
                            kubectl get deployments -n ${KUBERNETES_NAMESPACE}
                            kubectl get pods -n ${KUBERNETES_NAMESPACE} -l app=${APP_NAME}
                            kubectl get services -n ${KUBERNETES_NAMESPACE} -l app=${APP_NAME}
                        """
                    }
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    echo 'Performing post-deployment health check...'

                    withCredentials([file(credentialsId: 'kubeconfig-credential', variable: 'KUBECONFIG_FILE')]) {
                        sh """
                            cp \$KUBECONFIG_FILE ~/.kube/config

                            # Wait for pods to be ready
                            kubectl wait --for=condition=Ready pod -l app=${APP_NAME} -n ${KUBERNETES_NAMESPACE} --timeout=300s

                            # Get pod name for health check
                            POD_NAME=\$(kubectl get pods -n ${KUBERNETES_NAMESPACE} -l app=${APP_NAME} -o jsonpath='{.items[0].metadata.name}')
                            echo "Health checking pod: \$POD_NAME"

                            # Port forward for health check (run in background)
                            kubectl port-forward \$POD_NAME ${APP_PORT}:${APP_PORT} -n ${KUBERNETES_NAMESPACE} &
                            PF_PID=\$!

                            # Wait for port forward to establish
                            sleep 10

                            # Perform health check
                            for i in {1..10}; do
                                if curl -f http://localhost:${APP_PORT}/actuator/health >/dev/null 2>&1; then
                                    echo "✅ Health check passed!"
                                    break
                                fi
                                echo "Attempt \$i: Health check failed, retrying..."
                                sleep 10
                            done

                            # Clean up port forward
                            kill \$PF_PID || true

                            # Show final status
                            kubectl describe deployment/${APP_NAME} -n ${KUBERNETES_NAMESPACE}
                            kubectl logs -l app=${APP_NAME} -n ${KUBERNETES_NAMESPACE} --tail=50
                        """
                    }
                }
            }
        }

        stage('Cleanup Old Deployments') {
            steps {
                script {
                    echo 'Cleaning up old replica sets...'

                    withCredentials([file(credentialsId: 'kubeconfig-credential', variable: 'KUBECONFIG_FILE')]) {
                        sh """
                            cp \$KUBECONFIG_FILE ~/.kube/config

                            # Keep only the last 3 replica sets
                            kubectl get replicasets -n ${KUBERNETES_NAMESPACE} -l app=${APP_NAME} --sort-by=.metadata.creationTimestamp -o name | head -n -3 | xargs -r kubectl delete -n ${KUBERNETES_NAMESPACE} || true
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            // Clean up local Docker images to save space
            sh """
                docker rmi ${DOCKER_IMAGE}:${IMAGE_TAG} || true
                docker rmi ${DOCKER_IMAGE}:${LATEST_TAG} || true
                docker rmi ${DOCKER_IMAGE}:${GIT_COMMIT_SHORT} || true
            """

            // Clean workspace
            cleanWs()
        }

        success {
            echo 'Pipeline completed successfully!'

            script {
                // Get service information for success message
                withCredentials([file(credentialsId: 'kubeconfig-credential', variable: 'KUBECONFIG_FILE')]) {
                    sh """
                        cp \$KUBECONFIG_FILE ~/.kube/config
                        echo "🚀 Deployment Summary:"
                        echo "Namespace: ${KUBERNETES_NAMESPACE}"
                        echo "Image: ${DOCKER_IMAGE}:${IMAGE_TAG}"
                        echo "Git Commit: ${GIT_COMMIT_SHORT}"
                        kubectl get services -n ${KUBERNETES_NAMESPACE} -l app=${APP_NAME}
                    """
                }
            }

            // Send success notification (configure your notification method)
            // slackSend(
            //     channel: '#deployments',
            //     color: 'good',
            //     message: "✅ CartService deployed successfully to Kubernetes! Build: ${BUILD_NUMBER}, Commit: ${GIT_COMMIT_SHORT}, Image: ${DOCKER_IMAGE}:${IMAGE_TAG}"
            // )
        }

        failure {
            echo 'Pipeline failed!'

            // Send failure notification
            // slackSend(
            //     channel: '#deployments',
            //     color: 'danger',
            //     message: "❌ CartService Kubernetes deployment failed! Build: ${BUILD_NUMBER}, Commit: ${GIT_COMMIT_SHORT}"
            // )
        }

        unstable {
            echo 'Pipeline is unstable!'
        }
    }
}
