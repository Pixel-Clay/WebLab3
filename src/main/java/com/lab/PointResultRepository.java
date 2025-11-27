package com.lab;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class PointResultRepository {

    @PersistenceUnit(unitName = "web-lab2-pu")
    private EntityManagerFactory entityManagerFactory;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void save(PointResult result) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<PointResult> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PointResult> query = em.createQuery(
                "SELECT p FROM PointResult p ORDER BY p.id DESC", PointResult.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void deleteAll() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM PointResult").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}

