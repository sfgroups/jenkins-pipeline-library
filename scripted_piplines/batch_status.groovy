
node {
    stage('postbuild display sfgroups link') {
        String HTTP_URL= "www.sfgroups.com"
        description = "<a href='${HTTP_URL}'</a>" + "sfgroups"
        manager.addShortText(description, "black", "white", "1.5px", "white");
    }
    
    stage ('Post-Build') {
       /*manager.addShortText("VERSION Black on Lime Green", "black", "limegreen", "0px", "white")
        manager.addShortText("OBSOLETE YellowGrey5pxGrey", "yellow", "grey", "5px", "grey")

        manager.addBadge("warning.gif", "Warning test")
        manager.addWarningBadge("other warning test")

        manager.addShortText("Some text")
        manager.addShortText("\ntext")
        manager.addShortText("same line", "black", "lightgreen", "0px", "white")

        manager.addBadge("star-gold.gif", "icon from Jenkins")*/
        manager.addBadge("yellow.gif", "icon from groovy-postbuild plugin")
        manager.addBadge("/images/16x16/yellow.gif", "icon from Jenkins")
        manager.addBadge("completed.gif", "icon Completed")
       manager.addHtmlBadge("<H2>NODEJS</H2")
    }

    stage('update histroy'){
        manager.addShortText("deployed")
        manager.createSummary("gear2.gif").appendText("<h2>Successfully deployed</h2>", false)

    }

 stage('Update status') {
        def summary1 = createSummary(icon: "notepad.png", text: "started Builds:<br>")
        summary1.appendText("myBuild1: SUCCESS<br>", false)
        summary1.appendText("myBuild2: UNSTABLE<br>", false)

        currentBuild.description = "<a href='http://stackoverflow.com'>Stackoverbuild build" + env.BUILD_ID + "</a>"
    }


}