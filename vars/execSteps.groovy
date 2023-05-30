//https://blog.jdriven.com/2020/03/groovy-goodness-parse-yaml-with-yamlslurper/
//import groovy.yaml.YamlSlurper
def call(stageName) {
    ci = readYaml file: "ci.yaml"
    //echo "${ci.stage[0].steps}"
    ci.stage.each { stage ->
        echo "first $stage.name"
        echo "stage.nane: $stage.name, stageName: ${stageName}"
        if ("$stage.name" == "${stageName}") {
            echo "found $stage"
            stage.steps.each { step ->
                echo step.name
                echo step.exec
                //sh "${step.exec}"
                evaluate(step.exec)

            }
            return 0;
        }
        echo "Not found $stageName"
    }
}

