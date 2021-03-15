package io.hari.demo.dao;

import io.hari.demo.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Repository
public interface UserDao extends BaseDao<User> {

//    @Query(value = "insert into users_contacts(user_id, contacts_id) values(1, 3)",
//            nativeQuery = true)
//    @Modifying
//    void nativeInsertSQL();
}
