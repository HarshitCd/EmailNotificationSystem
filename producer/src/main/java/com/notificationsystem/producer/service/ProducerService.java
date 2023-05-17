package com.notificationsystem.producer.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.Properties;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import com.notificationsystem.producer.model.EmailModel;

@Service
public class ProducerService {

    private final String emailNotifierUrl = "localhost:5000/sendemail";
    
    public void sendEmail(EmailModel emailModel) {
        if(emailModel.getPriority().equals("high")){
            System.out.println("In High Priority");

            String bootstrapServers = "localhost:9092";
            String topic = "my-topic";

            // Kafka producer configuration
            Properties props = new Properties();
            props.put("bootstrap.servers", bootstrapServers);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            // Create the Kafka producer
            Producer<String, String> producer = new KafkaProducer<>(props);

            try {
                // Send a sample message to the topic
                String key = "key1";
                String value = "Hello, Kafka!";
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
                producer.send(record);

                System.out.println("Message sent successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close the producer
                producer.close();
            }
        } else {
            System.out.println("In Low Priority");

            CloseableHttpClient httpClient = HttpClients.createDefault();
            try {
                URL url = new URL (emailNotifierUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                byte[] input = emailModel.toString().getBytes("utf-8");
                os.write(input, 0, input.length);	
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
