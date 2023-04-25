def call( goals = "clean install") {
    echo "param goals: ${goals}"
    // sh "helloworld.sh ${world}"
    configFileProvider([configFile(fileId: 'global-maven-settings', variable: 'MAVEN_SETTINGS_XML')]) {
        sh "cat ${MAVEN_SETTINGS_XML}"
        sh "mvn -q -s ${MAVEN_SETTINGS_XML} ${goals}"
    }
}

def withConfigFileProvider( goals  = "clean install", configFileProviderID = "global-maven-settings") {
    echo "param goals: ${goals}, configFileProviderID: ${configFileProviderID}"
    configFileProvider([configFile(fileId: "${configFileProviderID}", variable: 'MAVEN_SETTINGS_XML')]) {
        //sh "cat ${MAVEN_SETTINGS_XML}"
        sh "mvn -q -s ${MAVEN_SETTINGS_XML} ${goals}"
    }
}

