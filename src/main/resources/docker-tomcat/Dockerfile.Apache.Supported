FROM tomcat:7.0.94-jre7

####RUN /usr/local/tomcat/bin/catalina.sh stop

#RUN sed -i 's/autoDeploy="true"/autoDeploy="false"/g' /usr/local/tomcat/conf/server.xml
ADD tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

RUN apt-get update
RUN apt-get install -y vim

#RUN export CLASSPATH=/usr/local/tomcat/webapps/incentivesweb/WEB-INF/classes

###RUN mkdir -p /usr/local/tomcat/webapps/incentivesweb/WEB-INF/classes
#ADD datasource.properties /usr/local/tomcat/webapps/incentivesweb/WEB-INF/classes/datasource.properties
#ADD incentives.properties /usr/local/tomcat/webapps/incentivesweb/WEB-INF/classes/incentives.properties
#ADD infrastructure.properties /usr/local/tomcat/webapps/incentivesweb/WEB-INF/classes/infrastructure.properties

#ADD incentivesweb-2019.04-SNAPSHOT.war /usr/local/tomcat/webapps/incentivesweb.war

###EXPOSE 8080

###CMD ["catalina.sh", "run"]