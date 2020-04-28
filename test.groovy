def host = 'localhost'
pipeline {
    agent{node('master')}
    stages {
    stage ('Download from git'){
        checkout([$class: 'GitSCM',
                    branches: [[name: '*/master']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [[$class: 'RelativeTargetDirectory',
                    relativeTargetDir: 'auto']],
                    submoduleCfg: [],
                    userRemoteConfigs: [[credentialsId: '',url: '']]])
                
      }
        
       
    }
    }
}
