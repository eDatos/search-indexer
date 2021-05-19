package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.util.ISO8601DateFormat;
import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

@Component
public class KafkaConsumerLauncher implements ApplicationListener<ContextRefreshedEvent> {

    protected static final Log                        LOGGER                              = LogFactory.getLog(KafkaConsumerLauncher.class);

    private Map<String, Future<?>>                    futuresMap                          = new HashMap<>();
    private final String                              CONSUMER_DATASET_1_NAME             = "search_consumer_dataset_1";
    private final String                              CONSUMER_PUBLICATION_1_NAME         = "search_consumer_publication_1";
    private final String                              CONSUMER_RECOVER_DATASET_1_NAME     = "search_consumer_recover_dataset_1";
    private final String                              CONSUMER_RECOVER_PUBLICATION_1_NAME = "search_consumer_recover_publication_1";

    @Autowired
    private ThreadPoolTaskExecutor                    threadPoolTaskExecutor;

    @Autowired
    private MetamacIndexerService<SpecificRecordBase> metamacIndexerService;

    @Autowired
    private SearchConfigurationService                searchConfigurationService;

    // Recover
    private KafkaConsumerContextInfo                  recoverDatasetContextInfo;
    private KafkaConsumerContextInfo                  recoverPublicationContextInfo;
    private Date                                      kafkaRecoverStatusLastDate;
    private static final String                       NINGUNA                             = "NINGUNA";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        if (ac.getParent() == null) {
            try {
                KafkaInitializeTopics.propagateCreationOfTopics(searchConfigurationService);

                futuresMap.put(CONSUMER_DATASET_1_NAME, startConsumerForDatasetTopic(ac));
                futuresMap.put(CONSUMER_PUBLICATION_1_NAME, startConsumerForPublicationTopic(ac));
                startKeepAliveKafkaThread(ac);
            } catch (Exception e) {
                LOGGER.error(e, e.getCause());
            }
        }
    }

    public void executeRecoverClient() throws MetamacException {
        ApplicationContext ac = ApplicationContextProvider.getApplicationContext();
        kafkaRecoverStatusLastDate = new Date();
        futuresMap.put(CONSUMER_RECOVER_DATASET_1_NAME, startRecoverConsumerForDatasetTopic(ac));
        futuresMap.put(CONSUMER_RECOVER_PUBLICATION_1_NAME, startRecoverConsumerForPublicationTopic(ac));
    }

    public IndexacionStatusDomain getKafkaRecoverStatus() throws MetamacException {
        if (recoverDatasetContextInfo != null && recoverDatasetContextInfo.isFinishedOnError()) {
            return IndexacionStatusDomain.FALLO;
        }

        if (recoverPublicationContextInfo != null && recoverPublicationContextInfo.isFinishedOnError()) {
            return IndexacionStatusDomain.FALLO;
        }

        if (futuresMap.containsKey(CONSUMER_RECOVER_DATASET_1_NAME) || futuresMap.containsKey(CONSUMER_RECOVER_PUBLICATION_1_NAME)) {
            return IndexacionStatusDomain.INDEXANDO;
        } else {
            return IndexacionStatusDomain.PARADO;
        }
    }

    public String getKafkaRecoverStatusLastDate() {
        if (kafkaRecoverStatusLastDate == null) {
            return NINGUNA;
        }
        return ISO8601DateFormat.format(kafkaRecoverStatusLastDate);
    }

    private void startKeepAliveKafkaThread(ApplicationContext context) throws MetamacException {
        KeepAliveKafkaThread keepAliveKafkaThread = new KeepAliveKafkaThread();
        threadPoolTaskExecutor.execute(keepAliveKafkaThread);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Future<?> startConsumerForDatasetTopic(ApplicationContext context) throws MetamacException {
        KafkaConsumerContextInfo consumerInfo = new KafkaConsumerContextInfo(searchConfigurationService.retrieveKafkaTopicDatasetsPublication(), CONSUMER_DATASET_1_NAME,
                searchConfigurationService.retrieveKafkaDatasetGroup());

        KafkaConsumerThread<DatasetVersionAvro> consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        KafkaConsumer<String, DatasetVersionAvro> consumerFromBegin = createConsumerFromCurrentOffset(consumerInfo);
        consumerThread.setConsumer(consumerFromBegin);
        consumerThread.setMetamacIndexerService(metamacIndexerService);
        consumerThread.setConsumerInfo(consumerInfo);

        return threadPoolTaskExecutor.submit(consumerThread);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Future<?> startRecoverConsumerForDatasetTopic(ApplicationContext context) throws MetamacException {
        recoverDatasetContextInfo = new KafkaConsumerContextInfo(searchConfigurationService.retrieveKafkaTopicDatasetsPublication(), CONSUMER_RECOVER_DATASET_1_NAME,
                searchConfigurationService.retrieveKafkaDatasetRecoverGroup());
        recoverDatasetContextInfo.setExitOnFinish(true);

        KafkaConsumerThread<DatasetVersionAvro> consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        KafkaConsumer<String, DatasetVersionAvro> consumerFromBegin = createConsumerFromBegin(recoverDatasetContextInfo);
        consumerThread.setConsumer(consumerFromBegin);
        consumerThread.setMetamacIndexerService(metamacIndexerService);
        consumerThread.setConsumerInfo(recoverDatasetContextInfo);

        return threadPoolTaskExecutor.submit(consumerThread);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Future<?> startConsumerForPublicationTopic(ApplicationContext context) throws MetamacException {
        KafkaConsumerContextInfo consumerInfo = new KafkaConsumerContextInfo(searchConfigurationService.retrieveKafkaTopicCollectionPublication(), CONSUMER_PUBLICATION_1_NAME,
                searchConfigurationService.retrieveKafkaPublicationGroup());

        KafkaConsumerThread<DatasetVersionAvro> consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        KafkaConsumer<String, DatasetVersionAvro> consumerFromBegin = createConsumerFromCurrentOffset(consumerInfo);
        consumerThread.setConsumer(consumerFromBegin);
        consumerThread.setMetamacIndexerService(metamacIndexerService);
        consumerThread.setConsumerInfo(consumerInfo);

        return threadPoolTaskExecutor.submit(consumerThread);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Future<?> startRecoverConsumerForPublicationTopic(ApplicationContext context) throws MetamacException {
        recoverPublicationContextInfo = new KafkaConsumerContextInfo(searchConfigurationService.retrieveKafkaTopicCollectionPublication(), CONSUMER_RECOVER_PUBLICATION_1_NAME,
                searchConfigurationService.retrieveKafkaPublicationRecoverGroup());
        recoverPublicationContextInfo.setExitOnFinish(true);

        KafkaConsumerThread<DatasetVersionAvro> consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        KafkaConsumer<String, DatasetVersionAvro> consumerFromBegin = createConsumerFromBegin(recoverPublicationContextInfo);
        consumerThread.setConsumer(consumerFromBegin);
        consumerThread.setMetamacIndexerService(metamacIndexerService);
        consumerThread.setConsumerInfo(recoverPublicationContextInfo);

        return threadPoolTaskExecutor.submit(consumerThread);
    }

    private Properties getConsumerProperties(KafkaConsumerContextInfo consumerInfo) throws MetamacException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, searchConfigurationService.retrieveKafkaBootStrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerInfo.getGroupId());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerInfo.getClientId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
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

    private KafkaConsumer<String, DatasetVersionAvro> createConsumerFromCurrentOffset(KafkaConsumerContextInfo consumerInfo) throws MetamacException {
        KafkaConsumer<String, DatasetVersionAvro> kafkaConsumer = new KafkaConsumer<>(getConsumerProperties(consumerInfo));
        kafkaConsumer.subscribe(Collections.singletonList(consumerInfo.getTopicName()));
        return kafkaConsumer;
    }

    private KafkaConsumer<String, DatasetVersionAvro> createConsumerFromBegin(KafkaConsumerContextInfo consumerInfo) throws MetamacException {
        KafkaConsumer<String, DatasetVersionAvro> kafkaConsumer = new KafkaConsumer<>(getConsumerProperties(consumerInfo));

        kafkaConsumer.subscribe(Collections.singletonList(consumerInfo.getTopicName()), new ConsumerRebalanceListener() {

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                LOGGER.info(Arrays.toString(partitions.toArray()) + " topic-partitions are revoked from this consumer");
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                LOGGER.info(Arrays.toString(partitions.toArray()) + "  topic-partitions are assigned to this consumer");

                Iterator<TopicPartition> topicPartitionIterator = partitions.iterator();
                Collection<TopicPartition> partitionsToFetch = new LinkedList<TopicPartition>();
                while (topicPartitionIterator.hasNext()) {
                    TopicPartition topicPartition = topicPartitionIterator.next();
                    LOGGER.info("Current offset is " + kafkaConsumer.position(topicPartition) + " committed offset is ->" + kafkaConsumer.committed(topicPartition));
                    LOGGER.info("Setting offset to beginning");
                    partitionsToFetch.add(topicPartition);
                }
                kafkaConsumer.seekToBeginning(partitionsToFetch);
            }

        });

        return kafkaConsumer;
    }

    class KeepAliveKafkaThread implements Runnable {

        @Override
        public void run() {
            while (alwaysWithDelay(1000)) {

                Iterator<Entry<String, Future<?>>> entryMapIterator = futuresMap.entrySet().iterator();

                while (entryMapIterator.hasNext()) {
                    Map.Entry<String, Future<?>> entry = entryMapIterator.next();

                    if (entry.getValue().isDone()) {
                        try {
                            switch (entry.getKey()) {
                                case CONSUMER_DATASET_1_NAME:
                                    LOGGER.info("The consumer " + entry.getKey() + " was disconected. Planning another consumer for the same topic...");
                                    futuresMap.put(CONSUMER_DATASET_1_NAME, startConsumerForDatasetTopic(ApplicationContextProvider.getApplicationContext()));
                                    break;
                                case CONSUMER_PUBLICATION_1_NAME:
                                    LOGGER.info("The consumer " + entry.getKey() + " was disconected. Planning another consumer for the same topic...");
                                    futuresMap.put(CONSUMER_PUBLICATION_1_NAME, startConsumerForPublicationTopic(ApplicationContextProvider.getApplicationContext()));
                                    break;
                                case CONSUMER_RECOVER_DATASET_1_NAME:
                                case CONSUMER_RECOVER_PUBLICATION_1_NAME:
                                    entryMapIterator.remove();
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            long retyrMS = 6000;
                            LOGGER.error("Impossible to replan Kafka consumers. Trying again at " + retyrMS + "ms", e);
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
