//https://blog.jdriven.com/2020/03/groovy-goodness-parse-yaml-with-yamlslurper/
import groovy.yaml.YamlSlurper
def call() {
       echo  $stage
       ci = readYaml file: "ci.yaml"
       steps = ci.steps
       steps.each {
              echo it.name
              sh it.exec
       }
 }

