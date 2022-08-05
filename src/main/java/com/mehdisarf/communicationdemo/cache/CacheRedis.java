package com.mehdisarf.communicationdemo.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehdisarf.communicationdemo.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CacheRedis {

    private Jedis jedis;
    private ObjectMapper objMapper;

    private static final String KEY = "users";

    @Autowired
    public CacheRedis(Jedis jedis, ObjectMapper objMapper) {
        this.jedis = jedis;
        this.objMapper = objMapper;
    }

    public Optional<Person> getCachedPerson(long id) {

        String jsonRepresentationOfThePerson = jedis.lindex(KEY, id - 1);

        Person person = null;
        try {
            person = objMapper.readValue(jsonRepresentationOfThePerson, Person.class);
        } catch (JsonProcessingException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return Optional.of(person);
    }

    public void cachePerson(Person person) {

        try {

            String jsonRepresentationOfThePerson = objMapper.writeValueAsString(person);
            jedis.rpush(KEY, jsonRepresentationOfThePerson);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Person> getAllCachedPeople() {

        List<Person> people = new ArrayList<>();
        List<String> jsonRepresentationOfAllPerson = jedis.lrange(KEY, 0, -1);

        for (String json : jsonRepresentationOfAllPerson) {

            Person person = null;
            try {
                person = objMapper.readValue(json, Person.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            people.add(person);
        }

        return people;
    }
}
