    
pipeline {
    agent{node('master')}
    stages {
        stage('Dowload project') {
            steps {
                script {
                    cleanWs()
                    //git(branch: 'master', credentialsId: 'IlyaBurakGit', url: 'https://github.com/ilyaburak/study_jenkins.git')
                }
                script {
                    echo 'Start download project'
                    checkout([$class                           : 'GitSCM',
                              branches                         : [[name: '*/master']],
                              doGenerateSubmoduleConfigurations: false,
                              extensions                       : [[$class           : 'RelativeTargetDirectory',
                                                                   relativeTargetDir: 'auto']],
                              submoduleCfg                     : [],
                              userRemoteConfigs                : [[credentialsId: 'IlyaBurakGit', url: 'https://github.com/ilyaburak/study_jenkins.git']]])
                }
            }
        }
        
    }

    
}
