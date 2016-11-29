package es.gobcan.istac.search.core.idxmanager.kafka.stream;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class Consumer<T extends SpecificRecordBase> {

    private KafkaConsumer<String, T> consumer = new KafkaConsumer<>(getProperties());

    public List<T> receive(String topic) {
        // fromCurrentOffset(topic);
        fromBeginningOffset(topic);
        // fromLastFiveOffset(topic);

        System.out.println("Reading topic:" + topic);

        while (true) {
            ConsumerRecords<String, T> records = consumer.poll(100);

            for (ConsumerRecord<String, T> record : records) {
                System.out.println("Current customer name is: " + record.value());
            }
            consumer.commitSync();
        }
    }

    private void fromBeginningOffset(String topic) {
        // Assign
        List<PartitionInfo> partitionInfos = null;
        partitionInfos = consumer.partitionsFor(topic);
        PartitionInfo partitionInfo = partitionInfos.iterator().next();
        TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
        List<TopicPartition> topicPartitionList = Collections.singletonList(topicPartition);
        consumer.assign(Collections.singletonList(topicPartition));
        consumer.seekToBeginning(topicPartitionList);
        System.out.println("Position: " + consumer.position(topicPartition));
    }

    private void fromLastFiveOffset(String topic) {
        // Assign
        List<PartitionInfo> partitionInfos = null;
        partitionInfos = consumer.partitionsFor(topic);
        PartitionInfo partitionInfo = partitionInfos.iterator().next();
        TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
        List<TopicPartition> topicPartitionList = Collections.singletonList(topicPartition);
        consumer.assign(Collections.singletonList(topicPartition));
        OffsetAndMetadata committed = consumer.committed(topicPartition);
        consumer.seek(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), committed.offset() - 5);
        System.out.println("Position: " + consumer.position(topicPartition));
    }

    private void fromCurrentOffset(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        // props.put("zookeeper.connect", "192.168.99.100:2181");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroDeserializer.class);
        props.put("session.timeout.ms", "10000");

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.99.100:8081");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        // props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

}
