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
properties(
        [
           parameters(

                    listParameters()

        )
      ]
)

def ci = null;

def listParameters(){
    ci = readYaml file: "ci.yaml"
    String params=""
    ci.params.each { param ->
        params += param +","
    }
    if (params != null && params.length() > 0 && params.charAt(params.length() - 1) == ',') {
        params = params.substring(0, params.length() - 1);
    }
    ch = [
           evaluate (params),
              //defaults
            choice(choices: ['opt1', 'opt2', 'opt3'], description: 'desc', name: 'bla')
    ]

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
                    ci.params.each  { p ->
                        println "$p"
                    }
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