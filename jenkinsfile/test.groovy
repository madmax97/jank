pipeline {
    agent{node('master')}
    stages { 
        stage('Download git repository') {
            steps { 
                script {
                    cleanWs()
                    withCredentials([
                        usernamePassword(credentialsId: 'srv_sudo',
                        usernameVariable: 'username',
                        passwordVariable: 'password')
                    ]) {
                        try {
                            sh "echo '${password}' | sudo -S docker stop max_d"
                            sh "echo '${password}' | sudo -S docker container rm max_d"
                        } catch (Exception e) {
                            currentBuild.result = 'FAILURE'
                            print 'problem'
                        
                        }

                    }
                }
                script {
                    checkout([$class                           : 'GitSCM',
                              branches                         : [[name: '*/master']],
                              doGenerateSubmoduleConfigurations: false,
                              extensions                       : [[$class           : 'RelativeTargetDirectory',
                                                                   relativeTargetDir: 'auto']],
                              submoduleCfg                     : [],
                              userRemoteConfigs                : [[credentialsId: 'ShushenachevMaxGit', url: 'https://github.com/madmax97/jank.git']]])
                }
            }
        }     
        
         stage ('Create docker image'){
            steps{
                script{
                    withCredentials([
                        usernamePassword(credentialsId: 'srv_sudo',
                        usernameVariable: 'username',
                        passwordVariable: 'password')
                    ]) {

                        sh "echo '${password}' | sudo -S docker build ${WORKSPACE}/auto -t docker_new"
                        sh "echo '${password}' | sudo -S docker run -d -p 8008:80 --name max_d -v /home/adminci/v_dir:/stat docker_new"
                    }
                }
            }
        }      
    
    stage ('Write info to file'){
            steps{
                script{
                    withCredentials([
                        usernamePassword(credentialsId: 'srv_sudo',
                        usernameVariable: 'username',
                        passwordVariable: 'password')
                    ]) {
                        sh "echo '${password}' | sudo -S docker exec -t nginx_alhassan bash -c 'df -h > /stat/stats.txt'"
			            sh "echo '${password}' | sudo -S docker exec -t nginx_alhassan bash -c 'top -n 1 -b >> /stat/stats.txt'"                     
                    }
                }
            }
        }
        
        stage ('Stop'){
            steps{
                script{
                    withCredentials([
                        usernamePassword(credentialsId: 'srv_sudo',
                        usernameVariable: 'username',
                        passwordVariable: 'password')
                    ]) {
                        sh "echo '${password}' | sudo -S docker stop max_d"                 
                    }
                }
            }
        }
        
    }
}
