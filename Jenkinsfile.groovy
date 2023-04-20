pipeline {
    agent {
        kubernetes {
            defaultContainer 'build'
            yaml """
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            some-label: some-label-value
        spec:
          containers:
          - name: build
            image: maven:3.6.3-jdk-11
            #custom image
            #image: caternberg/ci-toolbox
            command:
            - cat
            tty: true
            volumeMounts:
            - name: config-volume
              mountPath: /usr/local/bin
          volumes:
            - name: config-volume
              configMap:
                # Provide the name of the ConfigMap containing the files you want
                # to add to the container
                name: pod-build-scripts
                defaultMode: 0744
          """
        }
    }
    stages {
        stage('Stage1') {
            environment {
                WORLD = "Stockholm!!"
            }
            steps {
                // sh "ls -ltr /usr/local/bin"
                sh "helloworld.sh"
            }
        }
    }
}

