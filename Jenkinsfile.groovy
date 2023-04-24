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
          - name: build
            #image: maven:3.6.3-jdk-11
            #custom image
            image: caternberg/ci-toolbox:latest
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
                helloworld "World"
            }
        }
        stage('clone') {
            environment {
                REPO_URL=https://github.com/pipeline-demo-caternberg/maven-executable-jar-example.git
                REPO_BRANCH=master
                GIT_USERNAME=xxx
                GIT_PASSWORD="XXX"
                GIT_CLONE_DIR=.tmp/app
                GIT_SHALLOW_DEPTH=1
            }
            steps {
                sh "cloneAndCheckoutBranch.sh"
            }
        }
        stage('build') {
            environment {
                WORLD = "Stockholm!!"
                REPO_URL=https://github.com/pipeline-demo-caternberg/maven-executable-jar-example.git
                REPO_BRANCH=master
                GIT_USERNAME=xxx
                GIT_PASSWORD="XXX"
                GIT_CLONE_DIR=.tmp/app
                GIT_SHALLOW_DEPTH=1
            }
            steps {
                sh "cloneAndCheckoutBranch.sh"
                sh "helloworld.sh"
                sh "jq-sample-step.sh"
                sh "newSematicVersion.sh -m 1.1.1"
                sh "build.sh"
                //will fail because mvn is not installed yet
                //sh "mvn-version.sh"
            }
        }

        stage('Stage-Sequence-Wrapper') {
            environment {
                WORLD = "Berlin"
            }
            steps {
                sh "stage-sequence.sh"
//              sh '''
//                  find /usr/local/bin  -type f -name "*.sh" -exec bash {} \\;
//                '''
            }
        }
    }
}

