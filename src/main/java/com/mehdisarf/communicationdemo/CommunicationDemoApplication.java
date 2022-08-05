package com.mehdisarf.communicationdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehdisarf.communicationdemo.dao.PersonDAOImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class CommunicationDemoApplication {

    @Bean
    public Jedis jedis() {
        return new Jedis();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommunicationDemoApplication.class, args);
    }
}