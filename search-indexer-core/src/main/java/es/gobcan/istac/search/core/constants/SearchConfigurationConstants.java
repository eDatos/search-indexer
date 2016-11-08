package es.gobcan.istac.search.core.constants;

import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;

public class SearchConfigurationConstants extends ConfigurationConstants {

    public static final String SEARCH_HELP_URL = "search.help.url";

    public static final String DB_SEARCH_URL = "search.db.url";
    public static final String DB_SEARCH_USERNAME = "search.db.username";
    public static final String DB_SEARCH_PASSWORD = "search.db.password";
    public static final String DB_SEARCH_DRIVER_NAME = "search.db.driver_name";

    public static final String SEARCH_SOLR_ENDPOINT = "istac.idxmanager.solr.endpoint";
    public static final String SEARCH_SOLR_CORE_OR_COLLECTION = "istac.idxmanager.solr.core_or_collection";
    public static final String SEARCH_SOLR_CLOUD_SERVER_ENABLED = "istac.idxmanager.solr.cloud_server_enabled";

    public static final String SEARCH_ALFRESCO_URL = "istac.idxmanager.alfresco.url";
    public static final String SEARCH_ALFRESCO_USERNAME = "istac.idxmanager.alfresco.user";
    public static final String SEARCH_ALFRESCO_PASSWORD = "istac.idxmanager.alfresco.password";
    public static final String SEARCH_ALFRESCO_PATH = "istac.idxmanager.alfresco.pathRaiz";

    public static final String SEARCH_INDEXATION_WEB_CRON = "istac.idxmanager.indexacion.web.cron";
    public static final String SEARCH_INDEXATION_WEB_URL = "istac.idxmanager.indexacion.web.url";
    public static final String SEARCH_INDEXATION_GPE_CRON = "istac.idxmanager.indexacion.gpe.cron";

    public static final String SEARCH_KAFKA_BOOTSTRAP_SERVERS = "istac.idxmanager.kafka.bootstrap_server";
    public static final String SEARCH_KAFKA_SCHEMA_REGISTRY_URL = "istac.idxmanager.kafka.schema_registry_url";
    public static final String SEARCH_KAFKA_TOPIC_DATASETS = "istac.idxmanager.kafka.topic.datasets";
    public static final String SEARCH_KAFKA_TOPIC_PUBLICATIONS = "istac.idxmanager.kafka.topic.publications";
    public static final String SEARCH_KAFKA_GROUP = "istac.idxmanager.kafka.group";

    public static final String SEARCH_DEFAULT_CATEGORY_SCHEME_URN = "search.default.category_scheme.urn";

}