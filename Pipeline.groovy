library 'shared-lib' _
def mavenPod = libraryResource 'podagents/podTemplate-prod.yaml'

//https://github.com/devopscube/declarative-pipeline-examples/blob/master/parameters/Jenkinsfile.ActiveChoiceParameters
//TODO:
/*properties(
        [
                parameters([
                        string(defaultValue: '/data', name: 'Directory'),
                        string(defaultValue: 'Dev', name: 'DEPLOY_ENV')
                ])
        ]
)*/

//see https://stackoverflow.com/questions/44570163/jenkins-dynamic-declarative-pipeline-parameters
properties([
        [
                $class: 'RebuildSettings', autoRebuild: true, rebuildDisabled: false],
        parameters(
                script {
                    listParameters()
                }
        )
]
)

def ci = null;

def list_arguments(){
    lista = return_list()
    if ("${JOB_NAME}".contains("word")){
        ch = [
                choice(choices: ['opt1', 'opt2', 'opt3'], description: 'desc', name: 'bla'),
                choice(choices: ["${lista}"], description: 'some letter', name: 'ble')
        ]

    }else{
        ch = [
                choice(choices: ['opt1', 'opt2', 'opt3'], description: 'desc', name: 'bla')
        ]
    }
    return ch

}
pipeline {
    agent none
    stages {
        stage('Init') {
            agent {
                kubernetes {
                    //#deprectatedlabel 'mavenPod'
                    defaultContainer "build"
                    yaml mavenPod
                }
            }
            steps {

                script {
                    //read pipeline.yaml properties
                    ci = readYaml file: "ci.yaml"
                    echo ci.params
                    //sample common setting
/*                        properties(
                                [parameters(
                                        [string(defaultValue: 'value1', description: 'desc1', name: 'param1', trim: true)

                                        ]
                                )]
                        )*/
                }
            }
        }
        stage('build') {
            agent {
                kubernetes {
                    //#deprectatedlabel 'mavenPod'
                    defaultContainer "build"
                    yaml mavenPod
                }
            }
            steps {
                execSteps "build"
            }
        }
    }
}