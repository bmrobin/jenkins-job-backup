[![Build Status](https://travis-ci.com/bmrobin/jenkins-job-backup.svg?branch=master)](https://travis-ci.com/bmrobin/jenkins-job-backup)

![alt text](https://wiki.jenkins.io/download/attachments/2916393/logo.png?version=1&modificationDate=1302753947000&api=v2 "Jenkins")
# Jenkins Backup & Restore
An easy way to perform Jenkins Backup and Restore. The Jenkins Backup Plugin has some nice options, but this just seemed
simpler and more what I actually wanted. Plus I didn't have to shut down Jenkins to get the job backup information.

### Requirements ###
* Gradle >=4.x
* Java 1.8
* Requires a file `/src/main/resources/application.properties` with the following values substituted with your Jenkins configuration 
```properties
# Jenkins Configuration
backupConfiguration.jenkinsUsername=yourUsername
backupConfiguration.jenkinsPassword=yourPassword
backupConfiguration.jenkinsServerAddress=https://your-jenkins-server.com
```


### Execution ###
Two backup modes for persistence are currently available - file system and database. Modes are activated by Spring Profiles found in the JVM arguments.
These are required arguments and the application will fail immediately if a mode is not specified.

If using database mode, configure the database connection information in `application.properties`.
For example, the following will configure a local instance of PostgreSQL.
Refer to Spring Boot documentation for using other database vendors.
```properties
spring.datasource.url=jdbc:postgresql://localhost/backups
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL94Dialect
```

Compile and build
```bash
./gradlew clean build
```

Run
```bash
./gradlew -Dspring.profiles.active=[file|database] bootRun
```

Because the project is built with Spring Boot, the program can be executed directly with the `java -jar` command.
Simply specify the Spring Profile to activate and the location of the JAR file (typically in `./build/libs/`). 
