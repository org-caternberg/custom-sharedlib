def call(String goals) {
    echo "param goals: ${goals}"
    // sh "helloworld.sh ${world}"
    configFileProvider([configFile(fileId: 'global-maven-settings', variable: 'MAVEN_SETTINGS_XML')]) {
        sh "cat ${MAVEN_SETTINGS_XML}"
        sh("mvn  ${goals}")
    }
}

