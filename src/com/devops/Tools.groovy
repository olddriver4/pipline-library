package com.devops

def Printcolor(value, color){
    colors = ['red'   : "\033[40;31m >>>>>>>>>>>${value}<<<<<<<<<<< \033[0m",
            'blue'  : "\033[47;34m ${value} \033[0m",
            'green' : "^[[1;32m>>>>>>>>>>${value}>>>>>>>>>>^[[m",
            'green1' : "\033[40;32m >>>>>>>>>>>${value}<<<<<<<<<<< \033[0m" ]
    ansiColor('xterm') {
        println(colors[color])
    }
}

def GetBranch() {
    try{                    
        if("${branch}" != ""){
        println "----------webhook式触发-----------"
        branchName = branch - "refs/heads"
        branchName = sh(returnStdout: true,script: "echo ${branchName}|awk -F '/' '{print \$NF}'").trim()
        println "webhook触发的分支是: " + "${branchName}"
        }
    } catch(exc) { }          
        if("${params.repoBranch}" != 'null'){
        println "-----------手动方式触发------------"
        branchName = "${params.repoBranch}"
        println "手动触发的分支是: " + "${branchName}"
    } 
}

def GetCode(src, branchName, giturl, credentialsId) {
    if (src == "git") {
        println("拉取代码 --> 分支： ${branchName}")
        deleteDir() //clean up our workspace
        checkout([
            $class: 'GitSCM',
            branches: [[name: "${branchName}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [],
            gitTool: 'Default',
            submoduleCfg: [],
            userRemoteConfigs: [[url: "${giturl}", credentialsId: "${credentialsId}"]]
        ])
    }
}

def Build_text(branchName, projectName) {
    currentBuild.displayName = "Deploy on ${branchName} [#${BUILD_NUMBER}]"
    currentBuild.description = "Project: ${projectName}\n" +
                                "Branch: ${branchName}\n"
}