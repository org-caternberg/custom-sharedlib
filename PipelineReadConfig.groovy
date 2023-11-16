library 'shared-lib' _
def mavenPod = libraryResource 'podagents/podTemplate-prod.yaml'

//see https://stackoverflow.com/questions/57261787/use-object-returned-by-readyaml-from-file-in-declarative-jenkinsfile
def loadValuesYaml(){
    def valuesYaml = readYaml (file: './ci.yaml')
    return valuesYaml;
}

pipeline {
   agent {
        kubernetes {
            defaultContainer "build"
            yaml mavenPod
            //https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/cloudbees-ci-on-modern-cloud-platforms/kubernetes-using-external-pod-description-yaml
            //yamlFile "${params.agentPod}.yaml"
        }
    }
    stages {
        stage('Init') {
            steps {
                script{
                    valuesYaml = loadValuesYaml()
                    println valuesYaml.getClass()
                }
            }
        }
        stage('AppName') {
            steps {
                echo valuesYaml.appName
            }
        }
    }
}