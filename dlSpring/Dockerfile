#FROM openjdk:19-slim
#
#COPY wait-for-it.sh /wait-for-it.sh
#RUN chmod +x /wait-for-it.sh
#
#ARG JAR_FILE=target/dlSpring-0.0.1-SNAPSHOT.jar
#WORKDIR /opt/app
#COPY ${JAR_FILE} app.jar
#EXPOSE 8080
#ENTRYPOINT ["/wait-for-it.sh", "dldb:3306", "--timeout=30", "--", "java", "-jar", "app.jar"]