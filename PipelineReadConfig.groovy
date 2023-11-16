library 'shared-lib' _
def mavenPod = libraryResource 'podagents/podTemplate-prod.yaml'

//see https://stackoverflow.com/questions/57261787/use-object-returned-by-readyaml-from-file-in-declarative-jenkinsfile
def loadValuesYaml(){
    def valuesYaml = readYaml (file: './ci.yaml')
    return valuesYaml;
}
def getYamlValue(x){  
  return loadValuesYaml()[x];
}

def generateDynamicParams() {
    def params = []
    // Generate parameters dynamically
    // For example, let's add a boolean parameter
    params.add(booleanParam(name: 'ENABLE_TESTS', defaultValue: true, description: 'Enable tests?'))
    valuesYaml = loadValuesYaml()
    printl $valuesYaml
    valuesYaml.params.each { p ->
        
        params.add("$p")
    }
    
    // Add more parameters as needed
    return params
}


    //see https://stackoverflow.com/questions/44570163/jenkins-dynamic-declarative-pipeline-parameters
/*    properties(
           [
               parameters(
                        generateDynamicParams()
              )
          ]
    )
*/


pipeline {
    agent {
        kubernetes {
            defaultContainer "build"
            yaml mavenPod
            //https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/cloudbees-ci-on-modern-cloud-platforms/kubernetes-using-external-pod-description-yaml
            //yamlFile "${params.agentPod}.yaml"
        }
    }

   environment {
       //read from yaml and assign to env var
      APP_NAME=getYamlValue("appName")
   }
    stages {
        stage('Init') {
            steps {
                script{
                    valuesYaml = loadValuesYaml()
                    println valuesYaml.getClass()
                    valuesYaml.params.each { p ->
                        println "${p}"
                    }
                        //see https://stackoverflow.com/questions/44570163/jenkins-dynamic-declarative-pipeline-parameters
                    properties(
                           [
                               parameters(
                                        generateDynamicParams()
                              )
                          ]
                    )
                }
            }
        }
        stage('AppName') {
            steps {
                //option1
                echo valuesYaml.appName
                //option2
                echo getYamlValue("appName")
                //option3 use env vars
                sh "echo $APP_NAME"
            }
        }
        stage('ReadAndExecSteps'){
            steps {
                execSteps(valuesYaml, "build")
            }      
        }    
    }
}
