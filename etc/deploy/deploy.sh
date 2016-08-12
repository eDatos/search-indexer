#!/bin/sh

HOME_PATH=search
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH=/servers/metamac/tomcats/metamac01/webapps

LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml

scp -r etc/deploy deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH
scp search-internal-web/target/search-internal-*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/search-internal.war
scp buscador-istac-web/target/buscador*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/buscador.war
scp target/search-*solr_core.tar.gz deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/search-solr_core.tar.gz
ssh deploy@estadisticas.arte-consultores.com <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh
    
    sudo service metamac01 stop
    checkPROC "metamac"

        
    ###
    # SOLR
    ###
    sudo rm -rf /servers/solr/service/server/solr/istac/conf
    sudo rm -rf /servers/solr/service/server/solr/istac/core.properties
    sudo tar -xzf $TRANSFER_PATH/search-solr_core.tar.gz --overwrite -C /servers/solr/service/server/solr/
    sudo rm -rf $TRANSFER_PATH/search-solr_core.tar.gz

    sudo chown -R solr:solr /servers/solr/service/server/solr/istac
    sudo service $SOLR_SERVICE_NAME restart
    checkURL "http://localhost:8983/solr" "solr"

    ###
    # SEARCH-INTERNAL
    ###
    
    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/search-internal
    sudo mv $TRANSFER_PATH/search-internal.war $DEPLOY_TARGET_PATH/search-internal.war
    sudo unzip $DEPLOY_TARGET_PATH/search-internal.war -d $DEPLOY_TARGET_PATH/search-internal
    sudo rm -rf $DEPLOY_TARGET_PATH/search-internal.war
    
    # Restore Configuration
    sudo cp $HOME_PATH/environment_internal.xml $DEPLOY_TARGET_PATH/search-internal/WEB-INF/classes/search/environment.xml
    sudo cp $HOME_PATH/logback_internal.xml $DEPLOY_TARGET_PATH/search-internal/$LOGBACK_RELATIVE_PATH_FILE
    
    
    ###
    # BUSCADOR
    ###
    
    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/buscador
    sudo mv $TRANSFER_PATH/buscador.war $DEPLOY_TARGET_PATH/buscador.war
    sudo unzip $DEPLOY_TARGET_PATH/buscador.war -d $DEPLOY_TARGET_PATH/buscador
    sudo rm -rf $DEPLOY_TARGET_PATH/buscador.war
    
    # Restore Configuration
    sudo cp $HOME_PATH/environment.xml $DEPLOY_TARGET_PATH/buscador/WEB-INF/classes/buscador/environment.xml



    sudo chown -R metamac.metamac /servers/metamac
    sudo service metamac01 start
    checkURL "http://estadisticas.arte-consultores.com/search/" "metamac01"
    checkURL "http://estadisticas.arte-consultores.com/search-internal/" "metamac01"




EOF
