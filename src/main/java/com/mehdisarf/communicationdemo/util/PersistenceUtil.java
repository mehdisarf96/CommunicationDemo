package com.mehdisarf.communicationdemo.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {

    private static final PersistenceUtil singleton = new PersistenceUtil();
    private EntityManagerFactory emf;

    private PersistenceUtil() {
    }

    public static PersistenceUtil getInstance() {
        return singleton;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null)
            this.emf = Persistence.createEntityManagerFactory("communicationPU");

        return this.emf;
    }

    public void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }
}
