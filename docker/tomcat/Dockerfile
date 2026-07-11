# Tomcat 9 (javax.*, compatible avec le web.xml 4.0 du projet)
FROM tomcat:9-jdk17

# L'image officielle livre les applis d'admin dans webapps.dist : on active le manager
RUN cp -r /usr/local/tomcat/webapps.dist/manager /usr/local/tomcat/webapps/manager

# Utilisateur de déploiement (rôle manager-script pour Jenkins)
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

# Autorise l'accès au manager depuis d'autres conteneurs (Jenkins)
COPY manager-context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
