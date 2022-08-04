package com.mehdisarf.communicationdemo.controllers;

import com.mehdisarf.communicationdemo.entities.Person;
import com.mehdisarf.communicationdemo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> getAllPerson() throws InterruptedException {
        return personService.getAll();
    }

    @GetMapping("/persons/{personId}")
    public Person getPerson(@PathVariable("personId") Long id) {
        return personService.get(id);
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws InterruptedException {

        Person persistedPerson = personService.create(person);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(persistedPerson);
    }

    @PutMapping("/persons")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws InterruptedException {

        Person updatedPerson = personService.update(person);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(updatedPerson);
    }

    @DeleteMapping("/persons/{personId}")
    public ResponseEntity deletePerson(@PathVariable("personId") Long id) {
        personService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}