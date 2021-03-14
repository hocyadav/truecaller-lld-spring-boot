package io.hari.demo.dao;

import io.hari.demo.entity.GlobalContactDirectory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Repository
public interface GlobalContactDirectoryDao extends BaseDao<GlobalContactDirectory>{
    GlobalContactDirectory findByContactNum(BigInteger contactNum);
    List<GlobalContactDirectory> findAllByName(String name);
}
