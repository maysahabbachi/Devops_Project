FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/timesheet-1.0-SNAPSHOT.war timesheet-1.0-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/timesheet.war"]