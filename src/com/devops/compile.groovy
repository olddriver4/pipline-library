package com.devops

def Compile(compileType, buildversion, filename){
    switch(compileType) {
        case "golang":
            try {
                println "[Golang] start to compile the package ..."
                sh """
                    [ -n "`sudo docker images |grep golang:${buildversion}`" ] && sudo docker pull golang:${buildversion}
                    sudo docker run --rm -v `pwd`:`pwd` -w `pwd` golang:${buildversion} go mod download
                    sudo docker run --rm -v `pwd`:`pwd` -w `pwd` golang:${buildversion} go build -o ${filename}
                """
            }
            catch (err) {
                println "[Golang] failed to compile the package !"
            }
            break
        case "node":
            try {
                println "[Node] start to compile the package ..."
                sh """
                    [ -n "`sudo docker images |grep node:${buildversion}`" ] && sudo docker pull node:${buildversion}
                    sudo docker run --rm -v `pwd`:`pwd` -w `pwd` node:${buildversion} yarn
                    sudo docker run --rm -v `pwd`:`pwd` -w `pwd` node:${buildversion} yarn build
                    sudo chown -R jenkins.jenkins ./*
                    mv ${filename} ${env.PROJECT_NAME}
                    tar zcf ${env.PROJECT_NAME}.tar.gz ${env.PROJECT_NAME}
                """
            }
            catch (err) {
                println "[Node] failed to compile the package !"
            }
            break
        default:
            println("compileType ==> [golang|node]")
            break
    }
}