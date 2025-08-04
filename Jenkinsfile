pipeline {
  agent any

  environment {
    DOCKER_IMAGE = "near1715/edus-cart:${BUILD_NUMBER}"
    KUBECONFIG = "/var/lib/jenkins/.kube/config"
  }

  stages {
    stage('Clone Repo') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/main']],
          userRemoteConfigs: [[
            url: 'git@github.com:youruser/your-repo.git',
            credentialsId: 'cbe23c8d-51aa-4388-a14f-b81dfaea907e'
          ]]
        ])
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t $DOCKER_IMAGE .'
      }
    }

    stage('Push Docker Image') {
      steps {
        withCredentials([string(credentialsId: 'DOCKERHUB_PAT', variable: 'DOCKER_TOKEN')]) {
          sh """
            echo "$DOCKER_TOKEN" | docker login -u yourdockerhubuser --password-stdin
            docker push $DOCKER_IMAGE
          """
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        sh """
          sed -i 's|IMAGE_PLACEHOLDER|$DOCKER_IMAGE|g' k8s/deployment.yaml
          kubectl apply -f k8s/
        """
      }
    }
  }
}