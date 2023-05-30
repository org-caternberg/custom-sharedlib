library 'shared-lib' _
def mavenPod = libraryResource 'podagents/podTemplate-prod.yaml'

//https://github.com/devopscube/declarative-pipeline-examples/blob/master/parameters/Jenkinsfile.ActiveChoiceParameters
//TODO:
properties(
        [
                parameters([
                        string(defaultValue: '/data', name: 'Directory'),
                        string(defaultValue: 'Dev', name: 'DEPLOY_ENV')
                ])
        ]
)
def ci=null;
pipeline {
    agent none
        stages {
            stage('Init'){
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
            stage('build'){
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