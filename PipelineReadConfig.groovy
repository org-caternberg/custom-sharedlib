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

//https://blog.jdriven.com/2020/03/groovy-goodness-parse-yaml-with-yamlslurper/
//import groovy.yaml.YamlSlurper
def execCommonSteps(stageName) {
    commonConfig = readYaml text: libraryResource ("pipeline-config/ci.yaml")
    commonConfig.stage.each { stage ->
        //echo "stage.nane: $stage.name, stageName: ${stageName}"
        if ("$stage.name" == "${stageName}") {
            stage.steps.each { step ->
                echo step.name
                echo step.exec
                evaluate(step.exec)
            }
            return null;
        }
    }
}

def getCommonSteps (stageName){
    ciCoomonConfig = readYaml text: libraryResource ("pipeline-config/ci.yaml")
    echo "${ciCoomonConfig}"
    ciCoomonConfig.stage.each { it ->
        //echo "stage.nane: $it.name, stageName: ${stageName}"
        if ("$it.name" == "${it}") {
            return it.steps
        }
    }
    return null;
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
                script {
                    //Load config
                    valuesYaml = loadValuesYaml()
                    //println valuesYaml.getClass()

                    //generate properties, global options and parameters
                    //see https://docs.cloudbees.com/docs/cloudbees-ci/latest/automating-with-jenkinsfile/customizing-parameters
                    evaluate(valuesYaml.properties)

                    //generate global environment vars
                    env.APP_NAME = getYamlValue("appName")
                    loadValuesYaml().environment.each { environmentVar ->
                        evaluate("env."+environmentVar)
                    }

                    /**
                     * Examples on how to access values from yamlConfig
                     * */
                    //Example1
                    echo valuesYaml.appName
                    //Example2
                    echo getYamlValue("appName")
                    //Example3 use env vars
                    sh "echo ${env.APP_NAME}"
                    echo "ENV VAR EXAMPLE_KEY1 FROM yaml file: ${env.EXAMPLE_KEY1}"
                    echo "ENV VAR EXAMPLE_KEY2 FROM yaml file: ${env.EXAMPLE_KEY2}"
                    echo "PARAM BOOL1: ${params.bool1}"
                }
            }
        }

        stage('ReadAndExecSteps') {
            steps {
                //Execute custom steps
                execCommonSteps("build")

                //Execute custom steps
                execCustomSteps("build")

                //Example3 use env vars
                sh "echo ${env.APP_NAME}"
                echo "ENV VAR EXAMPLE_KEY1 FROM yaml file: ${env.EXAMPLE_KEY1}"
                echo "ENV VAR EXAMPLE_KEY2 FROM yaml file: ${env.EXAMPLE_KEY2}"
                echo "PARAM BOOL2: ${params.bool2}"
            }
        }
    }
}
