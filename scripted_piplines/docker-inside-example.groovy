
node {

    stage('Write file'){
        writeFile file: "usefulfile.txt", text: "This file is useful, need to archive it."
    }

    stage('Run Docker') {

        docker.image('sfgroups/nettools').inside() {
            stage('Build') {
                sh 'ls -la'
                sh 'hostname'
                sh 'pwd'
            }
        }
    }
}