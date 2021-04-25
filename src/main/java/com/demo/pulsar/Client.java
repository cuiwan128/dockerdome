package com.demo.pulsar;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class Client {
    private static  PulsarClient client;

    static {
        try {
            client = PulsarClient.builder()
                        .serviceUrl("pulsar://192.168.0.120:6650")
                        .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public  static PulsarClient  getClient(){
        return  client;
    }

}
