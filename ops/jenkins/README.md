# Jenkins
## Call different Pipeline
We have the following Jenkins folder Structure
```text
- Root
  - code  
    - DEV
      - develop
    - QA
  - e2e-test
    - DEV
      - develop
    - QA  
```
We want to call `Root/e2e-test/DEV/develop` from `Root/code/DEV/develop`
```groovy
 stage('E2E Tests') {
      when {
        anyOf {
          branch 'develop'  // Only run this step for branch develop, multiple branch pattern can be used
          branch pattern: "release/.*", comparator: "REGEXP";
        }
      }
      steps {
        script {
          try { // This will keep the build Successful even if this step has failed
            echo 'starting lem e2e'
            //This path is relative to the pipeline currently is running, make sure to use the real name of the folder, not the display name
            // ENVIRONMENT      = DEV | QA 
            // TARGET_JOB_NAME  = develop | release/.* 
            build "../../e2e-test/${ENVIRONMENT}/${TARGET_JOB_NAME == 'develop'?'develop':'master'}"  
          } catch (err) {
            echo err.getMessage()
          }
        }
      }
    }
```
This will work, for `develop` but not for release branches, because these are two separate GIT Projects.
So wee need to alter the release code and set `TARGET_JOB_NAME` to `master`.  

```groovy
// this snippet will mark the build unstable, if e2e fails, very useful if your test are still young and may fail often
catchError(message: "e2e builds failed", buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
  echo 'starting lem e2e'
  build "../../e2e-test/${ENVIRONMENT}/${TARGET_JOB_NAME == 'develop' ? 'develop' : 'master'}"
}
```
