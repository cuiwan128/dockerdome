package com.demo.pulsar;

import org.apache.pulsar.client.api.MessageRouter;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class ProducerTest {

    public static void main(String[] args) {
        try{
            PulsarClient client = Client.getClient();

            Producer<byte[]> producer = client.newProducer()
                    .topic("my-topic")
                    .create();

            // You can then send messages to the broker and topic you specified:
            for (int i = 0; i < 10; i++) {
                producer.send((i+"---My message").getBytes());
            }

            producer.closeAsync()
                    .thenRun(() -> System.out.println("Producer closed"))
                    .exceptionally((ex) -> {
                        System.err.println("Failed to close producer: " + ex);
                        return null;
                    });
        } catch (PulsarClientException p){
            p.getStackTrace();
        } finally {

        }

    }

}
