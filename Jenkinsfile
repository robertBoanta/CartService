pipeline {
  agent any

  environment {
    DOCKER_IMAGE = "near1715/edus-cart:${BUILD_NUMBER}"
    KUBECONFIG = "/var/lib/jenkins/.kube/config"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/master']],
          userRemoteConfigs: [[
            url: 'git@github.com:robertBoanta/CartService.git',
            credentialsId: 'cbe23c8d-51aa-4388-a14f-b81dfaea907e'
          ]]
        ])
      }
    }

    stage('Build Docker Image') {
      steps {
        sh "docker build -t $DOCKER_IMAGE ."
      }
    }

    stage('Push to DockerHub') {
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'DOCKERHUB_PAT',
          usernameVariable: 'DOCKER_USER',
          passwordVariable: 'DOCKER_PASS'
        )]) {
          sh(script: 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin', shell: '/bin/bash')
          sh "docker push $DOCKER_IMAGE"
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        script {
          // înlocuiește placeholder-ul cu imaginea corectă
          sh "sed -i 's|IMAGE_PLACEHOLDER|$DOCKER_IMAGE|g' k8s/deployment.yaml"
          
          // aplică în Kubernetes
          sh "kubectl apply -f k8s/"
          
          // opțional: scalează la 1 replica ca să eviți poduri pending
          sh "kubectl scale deployment cart-service --replicas=1 || true"
        }
      }
    }
  }

  post {
    always {
      echo '✅ Pipeline finished.'
    }
    failure {
      echo '❌ Pipeline failed!'
    }
  }
}
