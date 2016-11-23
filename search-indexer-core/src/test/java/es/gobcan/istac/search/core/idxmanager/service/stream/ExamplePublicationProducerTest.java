package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.io.IOException;
import java.util.Scanner;

import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;

import es.gobcan.istac.search.core.idxmanager.service.stream.mock.PublicationVersionAvro_TRANS_MAR_000001_Mock;

public class ExamplePublicationProducerTest {

    private static String topic = "COLLECTION_PUBLICATIONS";

    public static void main(String[] args) throws IOException {
        Producer<PublicationVersionAvro> producer = new Producer<>();

        System.out.println("Please input 'send' or 'exit'");

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();

            switch (input) {
                case "send":
                    producer.sendData(topic, PublicationVersionAvro_TRANS_MAR_000001_Mock.createPublicationVersionAvro());
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
