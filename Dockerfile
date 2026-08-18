FROM openjdk:26-ea-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=90"
ENTRYPOINT ["java","-jar","/app.jar"]