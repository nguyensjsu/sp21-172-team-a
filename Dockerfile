FROM openjdk:11
EXPOSE 9090
ADD ./build/libs/spring-cashier-1.0.jar /srv/spring-cashier-1.0.jar
CMD java -jar /srv/spring-cashier-1.0.jar
