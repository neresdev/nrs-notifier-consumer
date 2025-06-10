package com.nrs.consumer.config;

import com.nrs.consumer.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConsumerConfig {
    private static final String TRUSTED_PACKAGES = "com.nrs.consumer.dto";

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String kafkaBoostrapServers;

    @Value("${spring.kafka.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, NotificationDto> consumerFactory() {
        JsonDeserializer<NotificationDto> deserializer = new JsonDeserializer<>(NotificationDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages(TRUSTED_PACKAGES);
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBoostrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NotificationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
