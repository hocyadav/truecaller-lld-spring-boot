package io.hari.demo.dao;

import io.hari.demo.entity.Contact;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @Author Hariom Yadav
 * @create 15-03-2021
 */
@Repository
public class NativeSQLInsert {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertWithQuery(Contact contact) {
        entityManager.getTransaction().begin();

        System.out.println("contact = " + contact);
        String queryString = "insert into contacts (contact_name) values (?)";

        Query query = entityManager.createNativeQuery(queryString);
        query.executeUpdate();
//        entityManager.createNativeQuery(queryString)
//                .setParameter(1, contact.getContactName())
//                .executeUpdate();
        entityManager.getTransaction().commit();
    }
}
