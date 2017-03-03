#My docker file

FROM tomcat:9.0-alpine
MAINTAINER Antonin

ADD app.war /usr/local/tomcat/webapps/app.war

#Enable Debug
ENV JPDA_ADDRESS=8000
ENV JPDA_TRANSPORT=dt_socket