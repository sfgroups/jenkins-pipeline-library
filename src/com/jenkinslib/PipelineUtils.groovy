package com.jenkinslib

class PipelineUtils { 
    static def deleteFile(String name) { new File(name).delete() } 
}
