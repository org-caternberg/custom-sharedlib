library 'shared-lib' _
def mavenPod = libraryResource 'podagents/podTemplate-prod.yaml'

//https://github.com/devopscube/declarative-pipeline-examples/blob/master/parameters/Jenkinsfile.ActiveChoiceParameters
/*properties(
        [
                parameters([
                        string(defaultValue: '/data', name: 'Directory'),
                        string(defaultValue: 'Dev', name: 'DEPLOY_ENV')
                ])
        ]
)*/

//see https://stackoverflow.com/questions/44570163/jenkins-dynamic-declarative-pipeline-parameters
/*
properties(
        [
           parameters(
                    listParameters()
        )
      ]
)
*/

def ci = null;

def listParameters(){
    ch = [
            choice(choices: ['opt1', 'opt2', 'opt3'], description: 'desc', name: 'bla')
    ]

    return ch
}

//def myTmpOptions = options {timeout(time: 2, unit: 'MINUTES')}
pipeline {
    agent none




    stages {
        stage('Init') {
            agent {
                kubernetes {
                    defaultContainer "build"
                    yaml mavenPod
                    //https://docs.cloudbees.com/docs/cloudbees-ci-kb/latest/cloudbees-ci-on-modern-cloud-platforms/kubernetes-using-external-pod-description-yaml
                    //yamlFile "${params.agentPod}.yaml"
                }
            }
            steps {

                script {
                    //read pipeline.yaml properties
                    ci = readYaml file: "ci.yaml"
                    ci.params.each  { p ->
                        println "$p"
                    }
                    ci.options.each  { option ->
                        println "$option"
                    }
                    //sample common setting
                  /*   properties(
                                [parameters(
                                        [string(defaultValue: 'value1', description: 'desc1', name: 'param1', trim: true),//, add more
                                         ci.params.each  { p ->
                                             evaluate (p)
                                         }
                                        ]
                                )])*/
                        properties(
                            [
                                buildDiscarder(
                                        logRotator(
                                                daysToKeepStr: '7',
                                                numToKeepStr: '25'
                                        )
                                )
                            ]
                        )
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
            options {
                skipDefaultCheckout(true)
            }
            steps {
                execSteps "build"
            }
        }
    }
}