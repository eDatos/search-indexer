#!/bin/bash

HOME_PATH=search
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH=/servers/edatos-internal/tomcats/edatos-internal01/webapps
SOLR_TARGET_PATH=/servers/solr/solr

LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml
RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi

scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r etc/deploy deploy@192.168.10.16:$TRANSFER_PATH
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" search-indexer-internal-web/target/search-internal-*.war deploy@192.168.10.16:$TRANSFER_PATH/search-internal.war
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" target/search-*solr_core.tar.gz deploy@192.168.10.16:$TRANSFER_PATH/search-solr_core.tar.gz
ssh -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" deploy@192.168.10.16 <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh

    ###
    # SOLR
    ###
	sudo rm -rf $SOLR_TARGET_PATH/service/server/solr/istac/conf
    sudo rm -rf $SOLR_TARGET_PATH/service/server/solr/istac/core.properties
    sudo tar -xzf $TRANSFER_PATH/search-solr_core.tar.gz --overwrite -C $SOLR_TARGET_PATH/service/server/solr/
    
    sudo rm -rf $TRANSFER_PATH/search-solr_core.tar.gz

    sudo chown -R solr:solr $SOLR_TARGET_PATH/service/server/solr/istac
    sudo service solr restart
    checkURL "http://localhost:8983/solr" "solr"

    ###
    # SEARCH-INTERNAL
    ###
    
    if [ $RESTART -eq 1 ]; then
        sudo service edatos-internal01 stop
        checkPROC "edatos-internal"
    fi

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/search-internal
    sudo mv $TRANSFER_PATH/search-internal.war $DEPLOY_TARGET_PATH/search-internal.war
    sudo unzip $DEPLOY_TARGET_PATH/search-internal.war -d $DEPLOY_TARGET_PATH/search-internal
    sudo rm -rf $DEPLOY_TARGET_PATH/search-internal.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment_internal.xml $DEPLOY_TARGET_PATH/search-internal/WEB-INF/classes/search/environment.xml
    sudo cp $HOME_PATH/logback_internal.xml $DEPLOY_TARGET_PATH/search-internal/$LOGBACK_RELATIVE_PATH_FILE


    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-internal.edatos-internal /servers/edatos-internal
        sudo service edatos-internal01 start
    fi
    
	echo "Finished deploy"

EOF
