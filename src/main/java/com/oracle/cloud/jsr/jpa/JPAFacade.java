package com.oracle.cloud.jsr.jpa;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAFacade {

    private static EntityManagerFactory emf;

    private JPAFacade() {
    }

    public static void bootstrapEMF(String persistenceUnitName, Map<String, String> props) {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(persistenceUnitName, props);
            emf.createEntityManager().close(); //a hack to initiate 'eager' deployment of persistence unit during deploy time as opposed to on-demand
        }
    }

    public static EntityManager getEM() {
        if (emf == null) {
            throw new IllegalStateException("Please call bootstrapEMF first");
        }

        return emf.createEntityManager();
    }

    public static void closeEMF() {

        if (emf == null) {
            throw new IllegalStateException("Please call bootstrapEMF first");
        }

        emf.close();

    }

}
