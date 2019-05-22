FROM maven:3.6.1-jdk-8-alpine as BUILD
COPY src /usr/src/joboonja/src
COPY pom.xml /usr/src/joboonja
RUN mvn -f /usr/src/joboonja/pom.xml clean package

FROM tomcat:9.0.20-jre8
COPY --from=BUILD /usr/src/joboonja/target/joboonja.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080