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
//see https://docs.cloudbees.com/docs/cloudbees-ci/latest/automating-with-jenkinsfile/customizing-parameters
def generateDynamicParams() {
    println "GENERATE PARAMS"
    def params = []
    //ADD COMMON params
    params.add(booleanParam(name: 'ENABLE_TESTS', defaultValue: true, description: 'Enable tests?'))
    //ADD CUSTOM params from config yaml
    loadValuesYaml().params.each { p ->
        params.add(evaluate(p))
    }
    return params
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

def props= "properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '1', numToKeepStr: '1')), parameters([booleanParam(description: 'qwq', name: 'ewew')]), pipelineTriggers([pollSCM('* * * * *')])])"

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
                script {
                    valuesYaml = loadValuesYaml()
                    //println valuesYaml.getClass()
                    evaluate(valuesYaml.properties)

                    env.APP_NAME = getYamlValue("appName")
                    loadValuesYaml().environment.each { environmentVar ->
                        evaluate("env."+environmentVar)
                        println "ADD ENV: environmentVar"
                    }

                    /*Examples on how to access values from yamlConfig*/
                    //option1
                    echo valuesYaml.appName
                    //option2
                    echo getYamlValue("appName")
                    //option3 use env vars
                    sh "echo ${env.APP_NAME}"
                    echo "ENV VAR FROM yaml file: ${env.EXAMPLE_KEY1}"
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
