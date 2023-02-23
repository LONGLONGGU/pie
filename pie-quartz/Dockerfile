FROM java:8

ADD /target/pie-quartz.jar /home/pie/pie-quartz.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/home/pie/pie-quartz.jar"]
