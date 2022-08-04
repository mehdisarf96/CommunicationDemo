package com.mehdisarf.communicationdemo.services;

import com.mehdisarf.communicationdemo.dao.PersonDAOImpl;
import com.mehdisarf.communicationdemo.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonDAOImpl personDAO;

    @Autowired
    public PersonService(PersonDAOImpl personDAO) {
        this.personDAO = personDAO;
    }

    public List<Person> getAll() {
        return personDAO.getAll();
    }

    public Person get(long id) {
        return personDAO.get(id);
    }

    public Person create(Person person) {
        return personDAO.save(person);
    }

    public Person update(Person person) {
        return personDAO.update(person);
    }

    public void delete(long id) {
        personDAO.delete(id);
    }
}