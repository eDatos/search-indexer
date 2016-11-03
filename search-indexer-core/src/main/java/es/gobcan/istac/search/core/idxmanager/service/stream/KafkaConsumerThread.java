package es.gobcan.istac.search.core.idxmanager.service.stream;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

@Component
@Scope("prototype")
public class KafkaConsumerThread<T extends SpecificRecordBase> implements Runnable {

    protected static Log LOGGER = LogFactory.getLog(KafkaConsumerThread.class);

    private KafkaConsumer<String, T> consumer;
    private String topicName;
    private MetamacIndexerService metamacIndexerService;

    public void setConsumer(KafkaConsumer<String, T> consumer) {
        this.consumer = consumer;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setMetamacIndexerService(MetamacIndexerService metamacIndexerService) {
        this.metamacIndexerService = metamacIndexerService;
    }

    @Override
    public void run() {
        LOGGER.debug("Reading KAFKA topic: " + topicName);

        while (true) {
            ConsumerRecords<String, T> records = consumer.poll(100);

            for (ConsumerRecord<String, T> record : records) {
                try {
                    metamacIndexerService.indexarDatasetVersion((DatasetVersionAvro) record.value());
                } catch (ServiceExcepcion e) {
                    LOGGER.error("Imposible indexar recurso recibido desde KAFKA", e);
                }

            }
            consumer.commitSync();
        }
    }

}
