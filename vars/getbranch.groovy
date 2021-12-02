def call() {
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