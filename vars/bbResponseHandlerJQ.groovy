def call() {
    //Fake some Git JSON response data
    def response = sh(returnStdout: true, script: "curl -s https://www.githubstatus.com/api/v2/status.json")
    sh "echo ${response} -n | jq"
    def result = sh "echo -n  ${response} | jq '.page.name'"
    //Or: for more complex lines in shell, externalize to shell script f.e.
    // def result = sh(returnStdout: true, script: "jq-sample-step.sh ${jsonData) }${jqargs}")
    echo  result
    return result
}