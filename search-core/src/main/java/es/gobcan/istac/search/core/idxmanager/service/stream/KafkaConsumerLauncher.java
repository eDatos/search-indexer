package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.util.Collections;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.siemac.metamac.core.common.exception.MetamacException;
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

    protected static Log LOGGER = LogFactory.getLog(KafkaConsumerLauncher.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private MetamacIndexerService metamacIndexerService;

    @Autowired
    private SearchConfigurationService searchConfigurationService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        if (ac.getParent() == null) {
            try {
                startConsumerStream(ac);
            } catch (MetamacException e) {
                LOGGER.error(e);
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void startConsumerStream(ApplicationContext context) throws MetamacException {
        KafkaConsumerThread<DatasetVersionAvro> consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        KafkaConsumer<String, DatasetVersionAvro> consumerFromBegin = createConsumerFromBegin(searchConfigurationService.retrieveKafkaTopic());
        consumerThread.setConsumer(consumerFromBegin);
        consumerThread.setTopicName(searchConfigurationService.retrieveKafkaTopic());
        consumerThread.setMetamacIndexerService(metamacIndexerService);
        threadPoolTaskExecutor.submit(consumerThread);
    }

    private Properties getConsumerProperties() throws MetamacException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, searchConfigurationService.retrieveKafkaBootstrap());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, searchConfigurationService.retrieveKafkaGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, searchConfigurationService.retrieveKafkaSchemaRegistryUrl());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        return props;
    }

    private KafkaConsumer<String, DatasetVersionAvro> createConsumerFromBegin(String topic) throws MetamacException {
        KafkaConsumer<String, DatasetVersionAvro> kafkaConsumer = new KafkaConsumer<>(getConsumerProperties());
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        return kafkaConsumer;
    }

}
