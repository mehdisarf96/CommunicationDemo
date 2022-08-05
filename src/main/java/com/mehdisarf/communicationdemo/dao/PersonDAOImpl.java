package com.mehdisarf.communicationdemo.dao;

import com.mehdisarf.communicationdemo.cache.CacheRedis;
import com.mehdisarf.communicationdemo.entities.Person;
import com.mehdisarf.communicationdemo.util.PersistenceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonDAOImpl implements GenericPersonDAO {

    private static EntityManagerFactory emf = PersistenceUtil.getInstance().getEntityManagerFactory();
    private CacheRedis cache;

    @Autowired
    public PersonDAOImpl(CacheRedis cache) {
        this.cache = cache;
    }

    @Override
    public Person get(long id) {

        Optional<Person> cachedPerson = cache.getCachedPerson(id);
        if (cachedPerson.isPresent())
            return cachedPerson.get();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person thePerson = em.find(Person.class, id);
        em.getTransaction().commit();
        em.close();

        cache.cachePerson(thePerson);
        return thePerson;
    }

    @Override
    public List<Person> getAll() {
        List<Person> allCachedPeople = cache.getAllCachedPeople();
        if (allCachedPeople.size() != 0)
            return allCachedPeople;

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

        cache.cachePerson(person);
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