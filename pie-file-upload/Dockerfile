FROM java:8

ADD /target/pie-file-upload.jar /home/pie/pie-file-upload.jar
RUN mkdir -p /opt/files
VOLUME ["/opt/files"]
EXPOSE 6013
ENTRYPOINT ["java","-jar","/home/pie/pie-file-upload.jar"]
