 def call(String stage) {
       ci = readYaml file: "ci.yaml"
       echo ci."$stage".steps
      // for step in steps do
 }

