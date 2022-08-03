package com.mehdisarf.communicationdemo.controller;

import com.mehdisarf.communicationdemo.entities.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MyControllerWithSleep {

    @GetMapping("/sleeppersons")
    public List<Person> getAllPerson() throws InterruptedException {
        Thread.sleep(5000);
        List<Person> people = new ArrayList<>();
        people.add(new Person("mehdi", "sarf"));
        people.add(new Person("akbar", "aliani"));
        return people;
    }

    @PostMapping("/sleeppersons")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws InterruptedException {
        Thread.sleep(5000);
        person.setId(ThreadLocalRandom.current().nextLong(1, 1000));
        System.out.println(person + " : Is Saved.");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(person);
    }
}



