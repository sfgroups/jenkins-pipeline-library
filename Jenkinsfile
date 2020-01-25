
//@Library('jenkins-pipeline-library')_

 def stage(String label, Closure cl) {
     println "The stage is ${label}"
     cl()
     println "Exiting the stage"
 }
 node {

     stage('Docker Tag'){
         println "my-image:${env.BUILD_ID}"
     }

     stage('checkout'){
         git url: 'https://github.com/sfgroups/jenkins-pipeline-library.git'
        // sh 'printenv|sort'
         def branchName=sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
         println "Branch Name : ${branchName}"
     }

     stage('Branch Name from scm'){
     def scmVars = checkout scm
      def branchName = scmVars?.GIT_BRANCH
       println "Branch Name : ${branchName}"
     }

     stage('GIT status') {
        def commitNumber = sh(returnStdout: true, script: "git rev-list HEAD --count")?.trim()
        def LastCommitLog = sh(returnStdout: true, script: "git log --oneline|head -1")?.trim()
        def status=sh(returnStdout: true, script: "git show --oneline -s")?.trim()
         currentBuild.description = "${commitNumber} - ${status}"
/*
         git show --format="%H, %cn, %ce, %s" --no-patch
         git rev-parse HEAD
         git show --oneline -s
         git log --oneline|head -1
         */
       }

   stage('Example') {
       if (env.BRANCH_NAME == 'master') {
           echo 'I only execute on the master branch'
       } else {
           echo 'I execute elsewhere'
       }
   }

    stage('Say Hello World') {
     println 'Hello World'
    }
 }