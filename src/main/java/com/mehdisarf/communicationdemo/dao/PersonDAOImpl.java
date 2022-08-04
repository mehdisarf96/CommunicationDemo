package com.mehdisarf.communicationdemo.dao;

import com.mehdisarf.communicationdemo.entities.Person;
import com.mehdisarf.communicationdemo.util.PersistenceUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PersonDAOImpl implements GenericPersonDAO {

    private static EntityManagerFactory emf = PersistenceUtil.getInstance().getEntityManagerFactory();

    @Override
    public Person get(long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person thePerson = em.find(Person.class, id);
        em.getTransaction().commit();
        em.close();
        return thePerson;
    }

    @Override
    public List<Person> getAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Person> query = em.createQuery("from Person", Person.class);
        return query.getResultList();
    }

    @Override
    public Person save(Person person) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        em.close();
        return person;
    }

    @Override
    public Person update(Person person) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person updatedPerson = em.merge(person);
        em.getTransaction().commit();
        em.close();
        return updatedPerson;
    }

    @Override
    public void delete(long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person thePerson = em.find(Person.class, id);
        em.remove(thePerson);

        em.getTransaction().commit();
        em.close();
    }
}