//https://blog.jdriven.com/2020/03/groovy-goodness-parse-yaml-with-yamlslurper/
//import groovy.yaml.YamlSlurper
def call() {
      ci = readYaml file: "ci.yaml"
      //echo "${ci.steps}"
      ci.steps.each { step ->
           echo step.name
           echo step.exec
           //sh "${step.exec}"
          Eval.me(step.exec)
       }
 }

