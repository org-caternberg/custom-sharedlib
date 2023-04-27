def call( goals = "clean install") {
    echo "param goals: ${goals}"
    sh "mvn -q ${goals}"
}

