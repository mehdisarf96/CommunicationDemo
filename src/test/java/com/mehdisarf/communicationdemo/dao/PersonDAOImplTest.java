package com.mehdisarf.communicationdemo.dao;

import com.mehdisarf.communicationdemo.entities.Person;
import com.mehdisarf.communicationdemo.util.PersistenceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonDAOImplTest {

    //@Autowired kar nemikne. bayad mock koni.
    @Mock
    private PersonDAOImpl pDao;
    private static EntityManagerFactory emf = PersistenceUtil.getInstance().getEntityManagerFactory();

    @Test
    void testGet() {
        insertSomeRow();
        int personId = 1;
        Person thePerson = pDao.get(personId);
        assertEquals(thePerson.getFirstName(), "mehdi");
    }

    @Test
    void testGetAll() {
        insertSomeRow();
        List<Person> all = pDao.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void testSave() {
        Person p1 = new Person("mehdi", "sarf");

        assertNull(p1.getId());
        Person persistedPerson = pDao.save(p1);
        assertNotNull(p1.getId());
    }

    @Test
    void update() {
        insertSomeRow();
        Person p = new Person("mehdi", "sarfej");
        Person updatedPerson = pDao.update(p);
        assertEquals(updatedPerson.getLastName(), "sarfej");
    }

    @Test
    void delete() {
        insertSomeRow();
        pDao.delete(1);
        assertEquals(pDao.getAll().size(), 1);
    }

    @Test
    void testEffectOfUsingCacheForGet() {
        long id = 1L;

        long start = System.currentTimeMillis();
        pDao.get(id); // first hit
        long stop = System.currentTimeMillis();

        long firstTryDuration = stop - start;

        start = System.currentTimeMillis();
        pDao.get(id); // second hit
        stop = System.currentTimeMillis();

        long secondTryDuration = stop - start;

        System.out.println(firstTryDuration);
        System.out.println(secondTryDuration);
        assertTrue(secondTryDuration < firstTryDuration);
    }

    public static void insertSomeRow() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("communicationPU");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Person p1 = new Person("mehdi", "sarf");
        Person p2 = new Person("ahmad", "mahmood");

        entityManager.persist(p1);
        entityManager.persist(p2);

        entityManager.getTransaction().commit();
        entityManager.close();
        emf.close();
    }
}