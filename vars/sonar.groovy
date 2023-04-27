withSonarQubeEnv("sonarqubeserver") {
    //sh "sonar-scanner -Dproject.settings=./sonar-project.properties"
    //sh "mvn clean verify sonar:sonar"
    ansiColor('xterm') {
        //-Dsonar.webhooks.project=https://my-jenkins-domain.tld/sonarqube-webhook/
        // sh "${MVN_COMMAND_SONAR} -s ${MAVEN_SETTINGS_XML}"
    }
    sh "TODO"
    sh "sonar-scanner version"
}