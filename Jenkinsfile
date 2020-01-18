 node
 {
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

     stage('stus description') {
        def status=sh(returnStdout: true, script: "git show --oneline -s")?.trim()
         currentBuild.description = "${status}"
/*
         git show --format="%H, %cn, %ce, %s" --no-patch
         git rev-parse HEAD
         git show --oneline -s
         */
       }
 }