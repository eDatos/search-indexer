package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.util.Properties;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer<T extends SpecificRecordBase> {

    private KafkaProducer<String, T> producer = new KafkaProducer<>(getProperties());

    public void sendData(String topic, T data) {
        producer.send(new ProducerRecord<>(topic, data), (metadata, exception) -> {
            if (exception == null) {
                System.out.printf("Sent user: %s \n", data);
            } else {
                System.out.println("data sent failed: " + exception.getMessage());
            }
        });
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://192.168.99.100:8081");
        return props;
    }
}
