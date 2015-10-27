#!/bin/bash

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

# check smpt access
if [ -n "$SMTP_HOST_TCP_ADDR" ] &&  [ -n "$SMTP_HOST_TCP_PORT" ]; then

    echo "Detected SMTP HOST data..."
    export SMTP_HOST_TCP_ADDR="$SMTP_HOST_TCP_ADDR"
    export SMTP_HOST_TCP_PORT="$SMTP_HOST_TCP_PORT"
fi

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG -Dorg.uberfire.demo.notes.smtp.host=$SMTP_HOST_TCP_ADDR -Dorg.uberfire.demo.notes.smtp.port=$SMTP_HOST_TCP_PORT

