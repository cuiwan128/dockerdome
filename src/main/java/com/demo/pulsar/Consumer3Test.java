package com.demo.pulsar;

import org.apache.pulsar.client.api.*;

public class Consumer3Test {

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = Client.getClient();

        Consumer<byte[]> consumer = client.newConsumer()
                .topic("my-topic")
                .subscriptionName("my-subscription")
                .subscriptionType(SubscriptionType.Shared)
                .subscribe();
        while (true) {
            // Wait for a message
            Message msg = consumer.receive();
            try {
                // Do something with the message
                System.out.printf("Message received <<<===>>>: %s", new String(msg.getData()));

                // Acknowledge the message so that it can be deleted by the message broker
                consumer.acknowledge(msg);
            } catch (Exception e) {
                // Message failed to process, redeliver later
                consumer.negativeAcknowledge(msg);
            }
        }
    }


}