//https://blog.jdriven.com/2020/03/groovy-goodness-parse-yaml-with-yamlslurper/
//import groovy.yaml.YamlSlurper
def call(stageName) {
    ci = readYaml file: "ci.yaml"
    //echo "${ci.stage[0].steps}"
    ci.stage.each { stage ->
        echo "stage.nane: $stage.name, stageName: ${stageName}"
        if ("$stage.name" == "${stageName}") {
            commonSteps=getCommonSteps(stageName)
            commonSteps.each {
                echo step.name
                echo step.exec
                //sh "${step.exec}"
                evaluate(step.exec)
            }
            stage.steps.each { step ->
                echo step.name
                echo step.exec
                //sh "${step.exec}"
                evaluate(step.exec)
            }
            return 0;
        }
    }
}

def getCommonSteps (stageName){
    ciCoomonConfig = readYaml text: libraryResource ("pipeline-config/ci.yaml")
    echo "${ciCoomonConfig}"
    ciCoomonConfig.stage.each { it ->
        echo "stage.nane: $it.name, stageName: ${stageName}"
        if ("$it.name" == "${it}") {
            return it.steps
        }
    }
    return null;
}

