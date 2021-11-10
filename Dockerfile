FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/timesheet.war timesheet.war
ENTRYPOINT ["java","-jar","/timesheet.war"]