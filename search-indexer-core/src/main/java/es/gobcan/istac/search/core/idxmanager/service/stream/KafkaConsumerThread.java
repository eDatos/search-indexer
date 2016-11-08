package es.gobcan.istac.search.core.idxmanager.service.stream;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

        while (alwaysWithDelay()) {
            ConsumerRecords<String, T> records = consumer.poll(100);

            for (ConsumerRecord<String, T> record : records) {
                StringBuilder logMessage = new StringBuilder("Recibido mensaje desde KAFKA: Topic Name: ");
                // @formatter:off
                logMessage.append(topicName)
                .append(" Partition: ").append(record.partition())
                .append(" Offset: ").append(record.offset())
                .append(" Timestamp: ").append(record.timestamp())
                .append(" [").append(new DateTime(record.timestamp(), DateTimeZone.forID("Atlantic/Canary"))).append("]");
                // @formatter:on

                LOGGER.info(logMessage.toString());
                try {
                    // TODO FALTA INDEXAR UN MENSAJE DE TIPO PublicationVersionAvro
                    metamacIndexerService.indexarDatasetVersion((DatasetVersionAvro) record.value());
                    consumer.commitSync();
                } catch (ServiceExcepcion e) {
                    LOGGER.error("Imposible indexar recurso recibido desde KAFKA", e);
                }
            }
        }
    }

    private boolean alwaysWithDelay() {
        try {
            // sleep(2000);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return true;
    }

}
