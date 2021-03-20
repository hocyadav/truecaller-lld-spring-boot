package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;

import java.util.List;
import java.util.Objects;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
public abstract class BaseService<T> {
    BaseDao<T> dao;

    public BaseService(BaseDao<T> dao) {
        this.dao = dao;
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public T findById(Long entityId) {
        return dao.findById(entityId).get();
    }

    public T save(T entity) {
        return dao.save(entity);
    }

    public void printAll() {
        System.out.println("--- Printing All data ---");
        dao.findAll().stream().filter(Objects::nonNull).forEach(System.out::println);
    }

}
