####### BASE ############

FROM openshift/wildfly-8-centos

####### MAINTAINER ############

MAINTAINER "porcelli" "alexandre.porcelli@gmail.com"

####### ENVIRONMENT ############

ENV SMTP_HOST_TCP_ADDR localhost
ENV SMTP_HOST_TCP_PORT 1025

####### SYTEM USER ############

USER wildfly

####### DEMO FILES ############

ADD maven/extra/ROOT.war /wildfly/standalone/deployments/ROOT.war

ADD maven/extra /wildfly/extra/

####### COMMAND ############

CMD ["/wildfly/extra/execute.sh"]
