package es.gobcan.istac.search.core.idxmanager.service.stream;

import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;

public class ExamplePublicationConsumerTest {

    private static String topic = "COLLECTION_PUBLICATIONS";

    public static void main(String[] args) {
        Consumer<PublicationVersionAvro> consumer = new Consumer<>();

        consumer.receive(topic);

        while (true) {
            ;
        }
    }
}
