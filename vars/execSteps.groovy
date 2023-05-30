//https://blog.jdriven.com/2020/03/groovy-goodness-parse-yaml-with-yamlslurper/
//import groovy.yaml.YamlSlurper
def call() {
       ci = readYaml file: "ci.yaml"
        echo "${ci.steps}"
    steps = Arrays.asList(ci.steps)
      // steps = ci.steps
      //& echo steps
       steps.each {
              echo it.name
              sh it.exec
       }
 }

