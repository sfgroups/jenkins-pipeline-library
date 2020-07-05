# jenkins-pipeline-library

Demonstrates how to use a Shared Library in Jenkins pipelines. This repository defines a single function, `sayHello`, which will echo a greeting.

## Setup instructions

1. In Jenkins, go to Manage Jenkins &rarr; Configure System. Under _Global Pipeline Libraries_, add a library with the following settings:

    - Name: `jenkins-pipeline-library`
    - Default version: Specify a Git reference (branch or commit SHA), e.g. `master`
    - Retrieval method: _Modern SCM_
    - Select the _Git_ type
    - Project repository: `https://github.com/sfgroups/jenkins-pipeline-library.git`
    - Credentials: (leave blank)

2. Then create a Jenkins job with the following pipeline (note that the underscore `_` is not a typo):

    ```
    @Library('jenkins-pipeline-library')_

    stage('Demo') {

      echo 'Testing Jenkinslib'
   
      sayHello 'Jenkins Pipeline Test Script'

    }
    ```

This will output the following from the build:

```
[Pipeline] stage
[Pipeline] { (Demo)
[Pipeline] echo
Hello world
[Pipeline] echo
Hello, Jenkins Pipeline Test Script.
[Pipeline] }
[Pipeline] // stage
[Pipeline] End of Pipeline
Finished: SUCCESS
```

# jenkins-pipeline-library


Took the notes from this website: https://cleverbuilder.com/articles/jenkins-shared-library/

https://howtodoinjava.com/library/jaxrs-client-httpclient-get-post/

https://www.baeldung.com/httpclient-post-http-request
