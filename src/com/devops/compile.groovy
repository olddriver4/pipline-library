package com.devops

def Compile(compileType, args){
    switch(compileType) {
        case "golang":
            sh """
                go mod download
                go build -o ${args}
            """
            break
        case "node":
            sh """
                sudo docker run --rm -v `pwd`:`pwd` -w `pwd` ${args}
                sudo docker run --rm -v `pwd`:`pwd` -w `pwd` ${args}
            """
            break
        default:
            println("compileType ==> [golang|node]")
            break
    }
}