package io.hari.demo.dao;

import io.hari.demo.entity.Contact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Repository
public interface ContactDao extends BaseDao<Contact>{

    @Query(value = "insert into contacts(contact_name, jc_userid) values('omp', 1)",
            nativeQuery = true)
    @Modifying
    void nativeInsertSQL();
}
