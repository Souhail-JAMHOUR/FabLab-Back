pipeline {
    agent any 
    tools {
        maven "Maven 3.8.4"
    
    }

///////////////////////////////::
     environment {
        MYSQL_ROOT_LOGIN = credentials('mysql-root-login')
    }
/////////////////////////////////
    
    stages {

        //////////////////////////////////////
 stage('Deploy MySQL to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull mysql:8.0'
                sh 'docker network create dev || echo "this network exists"'
                sh 'docker container stop bnmed-mysql || echo "this container does not exist" '
                sh 'echo y | docker container prune '
                sh 'docker volume rm bnmed-mysql-data || echo "no volume"'

                sh "docker run --name bnmed-mysql --rm --network dev -v bnmed-mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_LOGIN_PSW} -e MYSQL_DATABASE=db_example  -d mysql:8.0 "
                sh 'sleep 20'
                sh "docker exec -i bnmed-mysql mysql --user=root --password=${MYSQL_ROOT_LOGIN_PSW} < script"
            }
        }
        
        ///////////////////////////////////////////


        
        stage('Build with maven') { 
            steps {
                // Run Maven on a Unix agent.
              
                //sh "mvn clean compile"     
                 sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }
        //stage('deploy') { 
            
          //  steps {
            //    sh "mvn package"
            //}
        //}
        stage('Build Docker image'){
          
            steps {
                echo "Hello Java Express"
                sh 'ls'
                sh 'docker build -t   bnmed/fablab-back:${BUILD_NUMBER} .'
            }
        }
        stage('Docker Login'){
            
            steps {
                 withCredentials([string(credentialsId: 'DockerId', variable: 'Dockerpwd')]) {
                    sh "docker login -u bnmed -p ${Dockerpwd}"
                }
            }                
        }
        stage('Docker Push'){
            steps {
                sh 'docker push  bnmed/fablab-back:${BUILD_NUMBER}'
            }
        }





        
        
        stage('Docker deploy spring to DEV'){
            steps {



                /////////////////////////////////////////::
                 echo 'Deploying and cleaning'
                sh 'docker image pull bnmed/fablab-back:${BUILD_NUMBER}'
                sh 'docker container stop bnmed/fablab-back:${BUILD_NUMBER} || echo "this container does not exist" '
                sh 'docker network create dev || echo "this network exists"'
                ////////////////////////////////////////////
               
                sh 'docker run -itd -p  0.0.0.0:8081:8080 --name fablab-back_${BUILD_NUMBER} --network dev bnmed/fablab-back:${BUILD_NUMBER}'
            }
        }
        stage('Archving') { 
            steps {
                 archiveArtifacts '**/target/*.jar'
            }
        }
    }
}

