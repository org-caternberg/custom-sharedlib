def call() {
    //Fake some Git JSON response data
    //This is an alternative option to replace the JSonSlurper
    def response = sh(returnStdout: true, script: "curl -s https://www.githubstatus.com/api/v2/status.json")
    def result = sh(returnStdout: true, script: "jq-sample-step.sh ${response} '.page.name'")
    echo  result
    return result
}