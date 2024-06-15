FROM gradle:jdk17
RUN apt update && apt install python3-pip -y
WORKDIR /APP
COPY build/libs/ .
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "aiitmohack-0.0.1-SNAPSHOT.jar"]

