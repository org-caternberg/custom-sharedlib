library 'shared-lib' _
def mavenPod = libraryResource 'podagents/podTemplate-prod.yaml'

//see https://stackoverflow.com/questions/57261787/use-object-returned-by-readyaml-from-file-in-declarative-jenkinsfile
def loadValuesYaml() {
    def valuesYaml = readYaml(file: './ci.yaml')
    return valuesYaml
}

def getYamlValue(x) {
    return loadValuesYaml()[x]
}

// Generate parameters dynamically
def generateDynamicParams() {
    def params = []
    //ADD COMMON params
    params.add(booleanParam(name: 'ENABLE_TESTS', defaultValue: true, description: 'Enable tests?'))
    //ADD CUSTOM params from config yaml
    loadValuesYaml().params.each { p ->
        params.add(evaluate(p))
    }
    return params
}

// Generate environment vars dynamically
def generateDynamicEnvVars() {
    def envVars = [:]
    // ADD COMMON  dynamic environment variables
    envVars['APP_NAME'] = getYamlValue("appName")
    envVars['DYNAMIC_VARIABLE'] = 'dynamic_value'
    envVars['ANOTHER_VARIABLE'] = 'another_dynamic_value'
    // ADD CUSTOM  dynamic environment variables
    loadValuesYaml().environment.each { key, value ->
        envVars["$key"] = "$value"
        println "$key = $value"
    }
    // Add more dynamic variables as needed
    return envVars
}

def execCustomSteps(stageName) {
        loadValuesYaml().stage.each { stage ->
        echo "stage.nane: $stage.name, stageName: ${stageName}"
        if ("$stage.name" == "${stageName}") {
            stage.steps.each { step ->
                echo step.name
                echo step.exec
                evaluate(step.exec)
            }
            return 0;
        }
    }
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

    environment {
        APP_NAME = getYamlValue("appName")
    }
    stages {
        stage('Init') {
            steps {
                script {
                    valuesYaml = loadValuesYaml()
                    //println valuesYaml.getClass()

                    properties([
                            // Generate dynamic parameters
                            //see https://stackoverflow.com/questions/44570163/jenkins-dynamic-declarative-pipeline-parameters
                            parameters(generateDynamicParams())
                    ])

                    // Generate dynamic environment variables
                   // Define the environment block dynamically
                    environment {
                        generateDynamicEnvVars().each { key, value ->
                            "${key}" "${value}"
                        }
                    }



                    /*Examples on how to access values from yamlConfig*/
                    //option1
                    echo valuesYaml.appName
                    //option2
                    echo getYamlValue("appName")
                    //option3 use env vars
                    sh "echo $APP_NAME"
                    echo "ENV VAR FROM yaml file: ${EXAMPLE_KEY1}"
                }
            }
        }

        stage('ReadAndExecSteps') {
            steps {
                sh "echo 'here is common sample step1'"
                sh "echo 'here is common sample step2'"
                execCustomSteps("build")
            }
        }
    }
}
