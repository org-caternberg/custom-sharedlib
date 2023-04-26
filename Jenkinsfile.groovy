library 'shared-lib' _
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
          - name: tools
            image: caternberg/ci-toolbox:latest
            imagePullPolicy: Always
            command:
            - cat
            tty: true
            volumeMounts:
            - name: config-volume
              mountPath: /usr/local/bin
          - name: sonar-scanner-cli
            image: sonarsource/sonar-scanner-cli
            imagePullPolicy: Always
            command:
            - cat
            tty: true
            volumeMounts:
            - name: config-volume
              mountPath: /usr/local/bin
          - name: build
            image: maven:3.6.3-jdk-11
            imagePullPolicy: Always
            command:
            - cat
            tty: true
            volumeMounts:
            - name: config-volume
              mountPath: /usr/local/bin
          volumes:
            #https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/#populate-a-volume-with-data-stored-in-a-configmap
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
        stage('helloworld') {
            steps {
                container("tools") {
                    helloworld "World"
                }
            }
        }
        stage('clone') {
            environment {
                REPO_URL = "https://github.com/pipeline-demo-caternberg/maven-executable-jar-example.git"
                REPO_BRANCH = "master"
                GIT_USERNAME = "xxx"
                GIT_PASSWORD = "XXX"
                GIT_CLONE_DIR = ".app"
                GIT_SHALLOW_DEPTH = "1"
            }
            steps {
                container("tools") {
                    sh "cloneAndCheckoutBranch.sh"
                }
            }
        }

        stage('build') {
            steps {
                container("build") {
                    dir(".app"){
                        stepsBuildMaven ()
                    }
                }
                container("tools") {
                    dir(".app"){
                        bbResponseHandlerJQ ()
                    }
                }
            }
        }
        stage('QA') {
            steps {
                container("sonar-scanner-cli") {
                    sh "sonar-scanner -h"
                }
            }
        }
    }
}
