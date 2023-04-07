FROM khipu/openjdk17-alpine
ARG JAR_FILE=build/libs/telegram-bot-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tg.jar
ENTRYPOINT ["java","-jar","/tg.jar"]