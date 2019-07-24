FROM ubuntu:16.04

# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

# Install Tomcat 7.0.54 from Apache Archives
RUN apt install -y wget && \
    wget https://archive.apache.org/dist/tomcat/tomcat-7/v7.0.54/bin/apache-tomcat-7.0.54.tar.gz && \
    tar xvf apache-tomcat-7.0.54.tar.gz && \
    mv apache-tomcat-7.0.54 /opt/tomcat && \
    rm apache-tomcat-7.0.54.tar.gz

# Why not
ADD tomcat-users.xml /opt/tomcat/conf/tomcat-users.xml

# Aid to debugging
RUN apt-get install -y vim

EXPOSE 8080

CMD [ "/opt/tomcat/bin/catalina.sh","run" ]