FROM openjdk:8u151-jre-alpine3.7
MAINTAINER servicedesk.domain.com
#Command to build from current (cloned) directory where Dockerfile is located:
#docker build . -t servicedesk-wallscreen:0.0.1
#Command to run the container:
#docker run -d -ti -p 8800:8800 --name=sd-wallscreen-app t3rmin41/servicedesk-wallscreen:0.0.1

# Create app directory
RUN mkdir -p /usr/lib/servicedesk-wallscreen
# Create db directory
RUN mkdir -p /usr/lib/servicedesk-wallscreen/db

COPY ./build/libs/com.domain.service.desk.wall.screen.app-0.0.1.jar /usr/lib/servicedesk-wallscreen/com.domain.service.desk.wall.screen.app-0.0.1.jar
COPY ./db/prod/bin.mv.db /usr/lib/servicedesk-wallscreen/db/prod/bin.mv.db

WORKDIR /usr/lib/servicedesk-wallscreen

EXPOSE 8800
#CMD "java -jar com.domain.service.desk.wall.screen.app-0.0.1.jar"
ENTRYPOINT ["java","-jar","com.domain.service.desk.wall.screen.app-0.0.1.jar"]
