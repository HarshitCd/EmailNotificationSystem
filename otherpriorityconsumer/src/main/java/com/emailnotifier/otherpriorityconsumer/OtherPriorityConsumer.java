package com.emailnotifier.otherpriorityconsumer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class OtherPriorityConsumer {
    private final String emailNotifierUrl = "localhost:5000/sendemail";
    public void scheduledComsumer() {
        String bootstrapServers = "localhost:9092";
        String topic = "my-topic";

        // Kafka consumer configuration
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("group.id", "my-consumer-group");

        // Create the Kafka consumer
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        try {
            // Subscribe to the topic
            consumer.subscribe(Collections.singletonList(topic));

            // Start consuming messages
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                records.forEach(record -> {
                    try {
                        URL url = new URL (emailNotifierUrl);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Content-Type", "application/json");
                        con.setRequestProperty("Accept", "application/json");
                        con.setDoOutput(true);
        
                        OutputStream os = con.getOutputStream();
                        byte[] input = record.value().getBytes("utf-8");
                        os.write(input, 0, input.length);	
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the consumer
            consumer.close();
        }
    }
}
