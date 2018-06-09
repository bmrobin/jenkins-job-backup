[![Build Status](https://travis-ci.com/bmrobin/jenkins-job-backup.svg?branch=master)](https://travis-ci.com/bmrobin/jenkins-job-backup)

![alt text](https://wiki.jenkins.io/download/attachments/2916393/logo.png?version=1&modificationDate=1302753947000&api=v2 "Jenkins")
# Jenkins Backup & Restore
An easy way to perform Jenkins Backup and Restore. The Jenkins Backup Plugin has some nice options, but this just seemed simpler and more what I actually wanted. Plus I didn't have to shut down Jenkins to get the job backup information.

### Requirements ###
* Gradle 4.6
* Java 1.8
* Requires a list of Jenkins jobs in `./src/main/resources/jobs.txt` that correspond to the jobs you wish to be backed up or restored


### Execution ###
Compile and build
```bash
./gradlew clean build
```

Execute with `argv[0] argv[1] argv[2]` for Jenkins username, password, and hostname, respectively.
