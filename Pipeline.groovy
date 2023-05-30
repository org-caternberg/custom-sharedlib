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

def ci = readYaml file: "ci.yaml"
echo ci.params

pipeline {
    agent any
        stages {
            stage('Init'){
                steps {
                    script {
                        //read pipeline.yaml properties
                        ci = readYaml file: "ci.yaml"
                        echo ci.params
                        //sample common setting
                        properties(
                                [parameters(
                                        [string(defaultValue: 'value1', description: 'desc1', name: 'param1', trim: true)

                                        ]
                                )]
                        )
                    }
                }
            }
        }
}