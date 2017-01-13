package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

@Component
public class KafkaConsumerLauncher implements ApplicationListener<ContextRefreshedEvent> {

    protected static Log                              LOGGER                      = LogFactory.getLog(KafkaConsumerLauncher.class);

    private Map<String, Future<?>>                    futuresMap;
    private final String                              CONSUMER_DATASET_1_NAME     = "consumer_dataset_1";
    private final String                              CONSUMER_PUBLICATION_1_NAME = "consumer_publication_1";

    @Autowired
    private ThreadPoolTaskExecutor                    threadPoolTaskExecutor;

    @Autowired
    private MetamacIndexerService<SpecificRecordBase> metamacIndexerService;

    @Autowired
    private SearchConfigurationService                searchConfigurationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        if (ac.getParent() == null) {
            try {
                futuresMap = new HashMap<>();
                futuresMap.put(CONSUMER_DATASET_1_NAME, startConsumerForDatasetTopic(ac));
                futuresMap.put(CONSUMER_PUBLICATION_1_NAME, startConsumerForPublicationTopic(ac));
                startKeepAliveKafkaThread(ac);
            } catch (MetamacException e) {
                LOGGER.error(e);
            }
        }
    }

    public void startKeepAliveKafkaThread(ApplicationContext context) throws MetamacException {
        KeepAliveKafkaThread keepAliveKafkaThread = new KeepAliveKafkaThread();
        threadPoolTaskExecutor.execute(keepAliveKafkaThread);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Future<?> startConsumerForDatasetTopic(ApplicationContext context) throws MetamacException {
        String topicDatasetsPublication = searchConfigurationService.retrieveKafkaTopicDatasetsPublication();
        KafkaConsumerThread<DatasetVersionAvro> consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        KafkaConsumer<String, DatasetVersionAvro> consumerFromBegin = createConsumerFromCurrentOffset(topicDatasetsPublication);
        consumerThread.setConsumer(consumerFromBegin);
        consumerThread.setTopicName(topicDatasetsPublication);
        consumerThread.setMetamacIndexerService(metamacIndexerService);
        return threadPoolTaskExecutor.submit(consumerThread);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Future<?> startConsumerForPublicationTopic(ApplicationContext context) throws MetamacException {
        String topicCollectionPublication = searchConfigurationService.retrieveKafkaTopicCollectionPublication();
        KafkaConsumerThread<DatasetVersionAvro> consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        KafkaConsumer<String, DatasetVersionAvro> consumerFromBegin = createConsumerFromCurrentOffset(topicCollectionPublication);
        consumerThread.setConsumer(consumerFromBegin);
        consumerThread.setTopicName(topicCollectionPublication);
        consumerThread.setMetamacIndexerService(metamacIndexerService);
        return threadPoolTaskExecutor.submit(consumerThread);
    }

    private Properties getConsumerProperties() throws MetamacException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, searchConfigurationService.retrieveKafkaBootStrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, searchConfigurationService.retrieveKafkaGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // Default is True
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000); // 10 s
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 900000); // 15 min, Max time for Bussiness Logic execution of consumer thread
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1); // The maximum number of records returned in a single call to poll()
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString().toLowerCase()); // Policy to follow when there are no confirmed offset

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, searchConfigurationService.retrieveKafkaSchemaRegistryUrl());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        return props;
    }

    private KafkaConsumer<String, DatasetVersionAvro> createConsumerFromCurrentOffset(String topic) throws MetamacException {
        KafkaConsumer<String, DatasetVersionAvro> kafkaConsumer = new KafkaConsumer<>(getConsumerProperties());
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        return kafkaConsumer;
    }

    class KeepAliveKafkaThread implements Runnable {

        @Override
        public void run() {
            while (alwaysWithDelay(1000)) {

                for (Map.Entry<String, Future<?>> entry : futuresMap.entrySet()) {
                    if (entry.getValue().isDone()) {
                        LOGGER.info("El consumidor " + entry.getKey() + " se ha desconectado. Planificando otro consumidor para el mismo Topic...");
                        try {
                            switch (entry.getKey()) {
                                case CONSUMER_DATASET_1_NAME:
                                    futuresMap.put(CONSUMER_DATASET_1_NAME, startConsumerForDatasetTopic(ApplicationContextProvider.getApplicationContext()));
                                    break;
                                case CONSUMER_PUBLICATION_1_NAME:
                                    futuresMap.put(CONSUMER_PUBLICATION_1_NAME, startConsumerForPublicationTopic(ApplicationContextProvider.getApplicationContext()));
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            long retyrMS = 6000;
                            LOGGER.error("Imposible replanificar consumidores de Kafka. Volviendolo a intentar en " + retyrMS + "ms", e);
                            alwaysWithDelay(60000);
                        }
                    }
                }
            }
        }

        private boolean alwaysWithDelay(long timeout) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
            return true;
        }
    }

}
