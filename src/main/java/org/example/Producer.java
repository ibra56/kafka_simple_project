package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;

public class Producer{
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    public static void main(String[] args){

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        

        HashMap<String, String> characters = new HashMap<String, String>();
        characters.put("hobbits", "Bilbo");
        characters.put("hobbits", "Frodo");
        characters.put("hobbits", "Gandalf");
        characters.put("hobbits", "Aragorn");
        characters.put("hobbits", "Legolas");
        characters.put("hobbits", "Gimli");
        characters.put("hobbits", "Boromir");
        characters.put("hobbits", "Sauron");
        characters.put("hobbits", "Saruman");

        for (HashMap.Entry<String, String> character: characters.entrySet()) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("lotRcharacters", character.getKey(), character.getValue());
            producer.send(producerRecord, (RecordMetadata recordMetadata, Exception exception) -> {
                if (exception ==null){
                    log.info("Received new metadata \n" +
                            "Topic: " + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp());
                    
                }else{
                    log.error("Error while producing message", exception);
                }
            });
            
        }
        producer.close();

    }
}