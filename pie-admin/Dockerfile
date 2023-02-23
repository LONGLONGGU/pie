FROM java:8
RUN mkdir -p /opt/files
ADD /target/pie-admin.jar /home/pie/pie-admin.jar
EXPOSE 6002
ENTRYPOINT ["java","-jar","/home/pie/pie-admin.jar"]
