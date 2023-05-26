//https://github.com/devopscube/declarative-pipeline-examples/blob/master/parameters/Jenkinsfile.ActiveChoiceParameters

//TODO:
//read pipeline.yaml properties
pipelineProperties = readYaml "properties.yaml"

pipeline {
    agent any
        stages {
            stage('Init'){
                steps {
                    script {
                        //sample common setting
                        properties([parameters([string(defaultValue: 'value1', description: 'desc1', name: 'param1', trim: true)])])
                        for(int param in pipelineProperties.params) {
                                 println(param.value);
                              }
                    }
                }
            }
        }
}