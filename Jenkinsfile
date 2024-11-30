pipeline {
    agent any

    stages {
        stage('Deploy to Kubernetes') {
            steps {
                withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'EKS-MetroMob', contextName: '', credentialsId: 'k8-token', namespace: 'webapps', serverUrl: 'https://261BA33CB087532BE6F714B5652732F4.gr7.ap-south-1.eks.amazonaws.com']]) {
    // some block
    
    sh "kubectl apply -f deployment-service.yaml"
}
            }
        }
    }
    
       stages {
        stage('Verify Deployment') {
            steps {
                withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'EKS-MetroMob', contextName: '', credentialsId: 'k8-token', namespace: 'webapps', serverUrl: 'https://261BA33CB087532BE6F714B5652732F4.gr7.ap-south-1.eks.amazonaws.com']]) {
    // some block
    
    sh "kubectl get all -n webapps"
}
            }
        }
    }
}
