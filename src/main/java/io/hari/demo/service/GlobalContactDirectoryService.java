package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.dao.GlobalContactDirectoryDao;
import io.hari.demo.entity.GlobalContactDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Service
public class GlobalContactDirectoryService extends BaseService<GlobalContactDirectory>{
    @Autowired
    GlobalContactDirectoryDao contactDirectoryDao;

    public GlobalContactDirectoryService(BaseDao<GlobalContactDirectory> dao) {
        super(dao);
    }

    //m2 directly call from dao and test
    public GlobalContactDirectory searchGlobalContact(BigInteger contactNum) {
        final GlobalContactDirectory byContactNum = contactDirectoryDao.findByContactNum(contactNum);
        System.out.println("byContactNum = " + byContactNum);
        return byContactNum;
    }

    public List<GlobalContactDirectory> searchGlobalContact(String contactName) {
        final List<GlobalContactDirectory> allByName = contactDirectoryDao.findAllByName(contactName);
        System.out.println("allByName = " + allByName);
        return allByName;
    }
}
