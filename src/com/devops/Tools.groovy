package com.devops

def Printcolor(value, color){
    colors = ['red'   : "\033[40;31m >>>>>>>>>>>${value}<<<<<<<<<<< \033[0m",
            'blue'  : "\033[47;34m ${value} \033[0m",
            'green' : "^[[1;32m>>>>>>>>>>${value}>>>>>>>>>>^[[m",
            'green1' : "\033[40;32m >>>>>>>>>>>${value}<<<<<<<<<<< \033[0m" ]
    ansiColor('xterm') {
        log.info(colors[color])
    }
}

def GetBranch() {
    try{                    
        if("${branch}" != ""){
        log.info  "----------webhook式触发-----------"
        env.branchName = branch - "refs/heads"
        env.branchName = sh(returnStdout: true,script: "echo ${branchName}|awk -F '/' '{print \$NF}'").trim()
        log.info  "webhook触发的分支是: " + "${branchName}"
        }
    } catch(exc) { }          
        if("${params.repoBranch}" != 'null'){
        log.info "-----------手动方式触发------------"
        env.branchName = "${params.repoBranch}"
        log.info "手动触发的分支是: " + "${branchName}"
    } 
}

def GetCode(src) {
    if (src == "git") {
        log.info("拉取代码 --> 分支： ${branchName}")
        //deleteDir() //clean up our workspace
        sh "rm -rf ./*"
        checkout([
            $class: 'GitSCM',
            branches: [[name: "${branchName}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [],
            gitTool: 'Default',
            submoduleCfg: [],
            userRemoteConfigs: [[url: "${env.GIT_URL}", credentialsId: "${env.CREDENTIALSID}"]]
        ])
    }
}

def Build_text() {
    currentBuild.displayName = "Deploy on ${branchName} [#${BUILD_NUMBER}]"
    currentBuild.description = "Project: ${env.PROJECT_NAME}\n" +
                                "Branch: ${branchName}\n"
}