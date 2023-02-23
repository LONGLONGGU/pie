FROM java:8

ADD /target/pie-gateway.jar /home/pie/pie-gateway.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/home/pie/pie-gateway.jar"]
