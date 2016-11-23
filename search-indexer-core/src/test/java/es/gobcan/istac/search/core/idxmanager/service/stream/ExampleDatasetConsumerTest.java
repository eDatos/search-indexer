package es.gobcan.istac.search.core.idxmanager.service.stream;

import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;

public class ExampleDatasetConsumerTest {

    private static String topic = "DATASET_PUBLICATIONS";

    public static void main(String[] args) {
        Consumer<DatasetVersionAvro> consumer = new Consumer<>();

        consumer.receive(topic);

        while (true) {
            ;
        }
    }
}
