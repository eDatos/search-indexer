package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.io.IOException;
import java.util.Scanner;

import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;

public class ExampleProducerTest {

    private static String topic = "DATASET_PUBLICATIONS";

    public static void main(String[] args) throws IOException {
        Producer<DatasetVersionAvro> producer = new Producer<>();

        System.out.println("Please input 'send' or 'exit'");

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();

            switch (input) {
                case "send":
                    producer.sendData(topic, DatasetVersionAvro_TRANS_MAR_00000_Mock.createDataSetVersionAvro());
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please input 'exit'");
            }
        }
    }

}
