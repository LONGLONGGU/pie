FROM java:8
RUN mkdir -p /opt/files
ADD /target/pie-auth.jar /home/pie/pie-auth.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/home/pie/pie-auth.jar"]
