pipeline {
    agent{node('master')}
    stages {
    stage ('Download from git'){
        checkout([$class: 'GitSCM',
                    branches: [[name: '*/master']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [[$class: 'RelativeTargetDirectory',
                    relativeTargetDir: 'test']],
                    submoduleCfg: [],
                    userRemoteConfigs: [[credentialsId: 'IlyaBurakGit',url: 'https://github.com/ilyaburak/study_jenkins.git']]])
                
      }
        
       
    }
    
}
