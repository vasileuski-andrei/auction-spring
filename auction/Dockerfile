FROM khipu/openjdk17-alpine
ARG JAR_FILE=build/libs/auction-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} auction.jar
ENTRYPOINT ["java","-jar","/auction.jar"]