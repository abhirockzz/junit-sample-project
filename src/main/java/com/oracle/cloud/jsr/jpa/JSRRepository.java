package com.oracle.cloud.jsr.jpa;

import com.oracle.cloud.jsr.jpa.entities.JavaEESpecification;
import java.util.List;
import javax.persistence.EntityManager;

public class JSRRepository {

    /**
     * Fetches JSR info, given JSR ID
     *
     * @param id JSR ID
     * @return JSR info
     */
    public JavaEESpecification get(String id) {
        EntityManager em = null;
        JavaEESpecification spec = null;
        try {
            em = JPAFacade.getEM();
            spec = em.find(JavaEESpecification.class, Integer.parseInt(id));
        } catch (Exception e) {
            //on purpose
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return spec;
    }

    /**
     * Fetches ALL JSRs
     *
     * @return List of JSRs
     */
    public List<JavaEESpecification> all() {
        EntityManager em = null;
        List<JavaEESpecification> specifications = null;
        try {
            //em = emf.createEntityManager();
            em = JPAFacade.getEM();
            specifications = em.createQuery("SELECT c FROM JavaEESpecification c").getResultList();
        } catch (Exception e) {
            throw e;
        } finally {

            if (em != null) {
                em.close();
            }

        }

        return specifications;
    }

    /**
     * Seeds a new JSR
     *
     * @param jsr new JSR info
     */
    public void newJSR(JavaEESpecification jsr) {
        EntityManager em = null;

        try {
            em = JPAFacade.getEM();
            em.getTransaction().begin();
            em.persist(jsr);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {

            if (em != null) {
                em.close();
            }
        }

    }

    /**
     * updates description for an existing JSR
     *
     * @param spec JSR info containing updated description
     */
    public void updateJSRDescription(JavaEESpecification spec) {
        EntityManager em = null;

        try {
            em = JPAFacade.getEM();
            em.getTransaction().begin();
            em.merge(spec);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {

            if (em != null) {
                em.close();
            }

        }

    }
}
