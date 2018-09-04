#!/usr/bin/env groovy
@Library('jenkins-pipeline-library')_

/* An example of using a declarative pipeline syntax along with dynamic parameters
   NOTES:
   - The dynamic parameters are not natively supported in the declarative syntax,
     so I use of the script syntax in the declarative step.
   - The parameters are interactive input that user needs to provide at run time
*/

pipeline {

    options {
        // Keep only 10 last build
        buildDiscarder(logRotator(numToKeepStr:'10'))

        // Timeout after 30 minutes
        timeout(time: 30, unit: 'MINUTES')
    }

    agent { node { label 'master' } }


    stages {
				
		stage('Init') {
			steps {
			echo 'Testing Jenkinslib'
			sayHello 'Dave'  
			println getNodeIP()
			}
		}
  
      // Get user input
        // (I generate options and write them to file in workspace)
        stage('Select') {
            steps {
                script {
                    // This sample code can be replaced with any shell or groovy code that can run and generate your options
                    sh "echo 'Option 1\nOption 2\nOption 3' > ${WORKSPACE}/options"

                    list = readFile(file: "${WORKSPACE}/options")
                    option = input(
                            message: 'Select option', ok: 'Select',
                            parameters: [choice(name: 'Options', choices: list, description: '')]
                    )
                }
                echo "Selected option is: ${option}"
            }
        }
        // This is to allow user to confirm hist selection (are you sure?)
        stage('Confirm') {
            steps {
                script {
                    confirm = input(
                            message: "Are you sure about ${option}?",
                            parameters: [booleanParam(name: 'Yes!', defaultValue: false, description: '')]
                    )

                    // Proceed or abort
                    if (confirm) {
                        echo "Confirmed. Proceeding with ${option}..."
                    } else {
                        echo "Aborted on user action"
                        sh "exit 1"
                    }
                }
            }
        }

        // Parallel execution
        stage('Run process') {
            steps {
                parallel (
                    Thread1: {
                        sh "echo Thread 1 running with option ${option}"
                    },
                    Thread2: {
                        sh "echo Thread 2 running with option ${option}"
                    }
                )
            }
        }

        stage('Finish') {
            steps {
                echo "Build finished"
            }
        }
    }

    // Post build actions
    // Make sure you have email server configured to allow sending mail from Jenkins
    post {
        always {
            echo "Always run..."
        }

        success {
            mail (
                    to: emailextrecipients(['test@gmail.com', 'test@gmail.com']),
                    mimeType: "text/html",
                    subject: "[Jenkins] ${JOB_BASE_NAME} build ${BUILD_NUMBER} passed",
                    body: "<h3>You build passed!</h3><br>"
            )
        }

        failure {
            mail (
                    to: emailextrecipients(['test@gmail.com', 'test@gmail.com']),
                    mimeType: "text/html",
                    subject: "[Jenkins] ${JOB_BASE_NAME} build ${BUILD_NUMBER} failed!",
                    body: "<h3>${JOB_BASE_NAME} build ${BUILD_NUMBER} failed!</h3><br>" +
                            "Go to build page ${BUILD_URL} for more info"
            )
        }
    }
}

