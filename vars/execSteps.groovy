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
    ciCoomonConfig = libraryResource ("sharedlib/config-commons.yaml/ci.yaml")
    echo "${ciCoomonConfig}"
    ciCoomonConfig.stage.each { stage ->
        echo "stage.nane: $stage.name, stageName: ${stageName}"
        if ("$stage.name" == "${stageName}") {
            return stage.steps
        }
    }
    return null;
}

