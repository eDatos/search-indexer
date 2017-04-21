package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

@Component
@Scope("prototype")
public class KafkaConsumerThread<T extends SpecificRecordBase> implements Runnable {

    protected static Log                              LOGGER                       = LogFactory.getLog(KafkaConsumerThread.class);

    private static final String                       MAX_POOL_MSG                 = "We have set a poll of 1 message at most. This error can not be given.";

    private KafkaConsumer<String, T>                  consumer;
    private KafkaConsumerContextInfo                  consumerInfo;
    private MetamacIndexerService<SpecificRecordBase> metamacIndexerService;
    private int                                       numberOfPollCallsWithoutData = 0;

    public void setConsumer(KafkaConsumer<String, T> consumer) {
        this.consumer = consumer;
    }

    public void setConsumerInfo(KafkaConsumerContextInfo consumerInfo) {
        this.consumerInfo = consumerInfo;
    }

    public void setMetamacIndexerService(MetamacIndexerService<SpecificRecordBase> metamacIndexerService) {
        this.metamacIndexerService = metamacIndexerService;
    }

    @Override
    public void run() {
        LOGGER.info("Consumer for KAFKA in Topic: " + consumerInfo.getTopicName() + " ClientId: " + consumerInfo.getClientId() + " GroupId: " + consumerInfo.getGroupId());

        try {

            Map<Integer, Long> pendigOffsetsToCommit = new HashMap<Integer, Long>(); // K:partition, V:offset

            while (alwaysWithDelay()) {
                // Milliseconds, spent waiting in poll if data is not available in the buffer
                ConsumerRecords<String, T> records = consumer.poll(100);

                if (records.count() > 1) {
                    LOGGER.error(MAX_POOL_MSG);
                    throw new RuntimeException(MAX_POOL_MSG);
                }

                if (records.isEmpty()) {
                    if (checkIfFinishConsumer()) {
                        break;
                    } else {
                        continue;
                    }
                }

                // Process resources
                ConsumerRecord<String, T> record = records.iterator().next();

                if (pendigOffsetsToCommit.containsKey(record.partition()) && record.offset() == pendigOffsetsToCommit.get(record.partition())) {
                    LOGGER.debug("The current message already processed successfully");
                    if (commitSync(record)) {
                        pendigOffsetsToCommit.remove(record.partition());
                    }
                    continue;
                }

                StringBuilder logMessage = new StringBuilder("Received message from Kafka -> Topic Name: ");
                // @formatter:off
                logMessage
                    .append(consumerInfo.getTopicName())
                    .append(" ClientId: ").append(consumerInfo.getClientId())
                    .append(" GroupId: ").append(consumerInfo.getGroupId())
                    .append(" Partition: ").append(record.partition())
                    .append(" Offset: ").append(record.offset())
                    .append(" Timestamp: ").append(record.timestamp())
                    .append(" [").append(new DateTime(record.timestamp(), DateTimeZone.forID("Atlantic/Canary"))).append("]");
                // @formatter:on

                // Save pending to commit
                pendigOffsetsToCommit.put(record.partition(), record.offset());

                LOGGER.info(logMessage.toString());
                try {
                    LOGGER.info("Indexing resource: " + record.key());

                    metamacIndexerService.index(record.value());
                    consumer.commitSync();
                } catch (ServiceExcepcion e) {
                    LOGGER.error("Unable to process resource received from Kafka. The business of application has failed", e);
                    LOGGER.error("Process the next resource and discard the current message, key of message: " + record.key());
                }
            }

        } catch (Exception e) {
            LOGGER.error("An error has occurred in the Kafka client. Finishing the client.", e);
            consumerInfo.setFinishedOnError(true);
        } finally {
            LOGGER.info("Closing the consumer: Topic: " + consumerInfo.getTopicName() + " ClientId: " + consumerInfo.getClientId() + " GroupId: " + consumerInfo.getGroupId());
            consumer.close();
        }
    }

    private boolean commitSync(ConsumerRecord<String, T> record) {
        try {
            consumer.commitSync(Collections.singletonMap(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)));
            LOGGER.debug("Commited message: " + record.partition() + " : " + record.offset());
        } catch (CommitFailedException e) {
            LOGGER.debug("The message processing takes longer than the session timeout. The coordinator kicks the consumer out of the group (rebalanced)");
            return false;
        }
        return true;
    }

    private boolean alwaysWithDelay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return true;
    }

    private boolean checkIfFinishConsumer() {
        boolean exit = false;

        // Leave only if you are a consumer without waiting.
        // The first poll if always empty. We waited ten times for empty poll before ending
        if (consumerInfo.isExitOnFinish()) {
            if (numberOfPollCallsWithoutData >= 10) {
                exit = true;
            } else {
                numberOfPollCallsWithoutData++;
            }
        }
        return exit;
    }
}
