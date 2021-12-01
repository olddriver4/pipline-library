def pre_dingding() {
    post {
        success {
            wrap([$class: 'BuildUser']) {
                script {
                    //BUILD_USER_ID = "${env.BUILD_USER_ID}"
                    BUILD_USER = "${env.BUILD_USER}"
                    //BUILD_USER_EMAIL = "${env.BUILD_USER_EMAIL}"
                }
            }
            dingtalk (
                robot: "${env.DING_ID}", //dingding token
                type:'MARKDOWN',
                atAll: false,
                title: "Release: ${JOB_NAME}",
                //messageUrl: 'xxxx',
                text: [
                    //"<font color="#000066" size="5" >Aborted  [${JOB_NAME}](${BUILD_URL}) </font><br />",
                    "### [${branchName}/${env.PROJECT_NAME}](${BUILD_URL})",
                    ">- build event：**Build Starting ♻**",
                    ">- build user: ${BUILD_USER}",
                ]
            )
        }
    }
    
    steps {
        script {
            currentBuild.displayName = "Deploy on ${branchName} [#${BUILD_NUMBER}]"
            currentBuild.description = "Project: ${env.PROJECT_NAME}\n" +
                                        "Branch: ${branchName}\n"
        }
    }
}

def dingding() {
        post {
        success {
            wrap([$class: 'BuildUser']) {
                script {
                    //GIT_INFO
                    GIT_COMMT_ID = sh ( returnStdout: true, script: "git rev-parse --short HEAD")
                    GIT_COMMIT_USER = sh ( returnStdout: true, script: "git --no-pager show -s --format='%an'")
                    GIT_COMMIT_LOG = sh ( returnStdout: true, script: "git --no-pager show -s --format='%s'")

                    //BUILD_USER_ID = "${env.BUILD_USER_ID}"
                    BUILD_USER = "${env.BUILD_USER}"
                    //BUILD_USER_EMAIL = "${env.BUILD_USER_EMAIL}"
                }
            }
            dingtalk (
                robot: "${env.DING_ID}", //dingding token
                type:'MARKDOWN',
                atAll: false,
                title: "Success: ${JOB_NAME}",
                //messageUrl: 'xxxx',
                text: [
                    //"<font color="#000066" size="5" >Aborted  [${JOB_NAME}](${BUILD_URL}) </font><br />",
                    "### [${branchName}/${env.PROJECT_NAME}](${BUILD_URL})",
                    ">- build event：**Build Success ✅**",
                    ">- build time: ${currentBuild.durationString}",
                    ">- commit id: ${GIT_COMMT_ID}",
                    ">- commit user: ${GIT_COMMIT_USER}",
                    ">- chage log: ${GIT_COMMIT_LOG}",
                ]
            )
        }

        failure {
            wrap([$class: 'BuildUser']) {
                script {
                    //GIT_INFO
                    GIT_COMMT_ID = sh ( returnStdout: true, script: "git rev-parse --short HEAD")
                    GIT_COMMIT_USER = sh ( returnStdout: true, script: "git --no-pager show -s --format='%an'")
                    GIT_COMMIT_LOG = sh ( returnStdout: true, script: "git --no-pager show -s --format='%s'")

                    //BUILD_USER_ID = "${env.BUILD_USER_ID}"
                    BUILD_USER = "${env.BUILD_USER}"
                    //BUILD_USER_EMAIL = "${env.BUILD_USER_EMAIL}"
                }
            }
            dingtalk (
                robot: "${env.DING_ID}", //dingding token
                type:'MARKDOWN',
                atAll: false,
                title: "Failure: ${JOB_NAME}",
                //messageUrl: 'xxxx',
                text: [
                    "### [${branchName}/${env.PROJECT_NAME}](${BUILD_URL})",
                    ">- build event：**Build Fail❌**",
                    ">- build time: ${currentBuild.durationString}",
                    ">- commit id: ${GIT_COMMT_ID}",
                    ">- commit user: ${GIT_COMMIT_USER}",
                    ">- chage log: ${GIT_COMMIT_LOG}",
                ]
            )
        }

        unstable {
            wrap([$class: 'BuildUser']) {
                script {
                    //GIT_INFO
                    GIT_COMMT_ID = sh ( returnStdout: true, script: "git rev-parse --short HEAD")
                    GIT_COMMIT_USER = sh ( returnStdout: true, script: "git --no-pager show -s --format='%an'")
                    GIT_COMMIT_LOG = sh ( returnStdout: true, script: "git --no-pager show -s --format='%s'")

                    //BUILD_USER_ID = "${env.BUILD_USER_ID}"
                    BUILD_USER = "${env.BUILD_USER}"
                    //BUILD_USER_EMAIL = "${env.BUILD_USER_EMAIL}"
                }
            }
            dingtalk (
                robot: "${env.DING_ID}", //dingding token
                type:'MARKDOWN',
                atAll: false,
                title: "Unstable: ${JOB_NAME}",
                //messageUrl: 'xxxx',
                text: [
                    "### [${branchName}/${env.PROJECT_NAME}](${BUILD_URL})",
                    ">- build event：**Build Unstable❓**",
                    ">- build time: ${currentBuild.durationString}",
                    ">- commit id: ${GIT_COMMT_ID}",
                    ">- commit user: ${GIT_COMMIT_USER}",
                    ">- chage log: ${GIT_COMMIT_LOG}",
                ]
            )
        }

        aborted {
            wrap([$class: 'BuildUser']) {
                script {
                    //GIT_INFO
                    GIT_COMMT_ID = sh ( returnStdout: true, script: "git rev-parse --short HEAD")
                    GIT_COMMIT_USER = sh ( returnStdout: true, script: "git --no-pager show -s --format='%an'")
                    GIT_COMMIT_LOG = sh ( returnStdout: true, script: "git --no-pager show -s --format='%s'")

                    //BUILD_USER_ID = "${env.BUILD_USER_ID}"
                    BUILD_USER = "${env.BUILD_USER}"
                    //BUILD_USER_EMAIL = "${env.BUILD_USER_EMAIL}"
                }
            }
            dingtalk (
                robot: "${env.DING_ID}", //dingding token
                type:'MARKDOWN',
                atAll: false,
                title: "Aborted: ${JOB_NAME}",
                //messageUrl: 'xxxx',
                text: [
                    "### [${branchName}/${env.PROJECT_NAME}](${BUILD_URL})",
                    ">- build event：**Build Aborted❗**",
                    ">- build time: ${currentBuild.durationString}",
                    ">- commit id: ${GIT_COMMT_ID}",
                    ">- commit user: ${GIT_COMMIT_USER}",
                    ">- chage log: ${GIT_COMMIT_LOG}",
                ]
            )
        }
    }
}