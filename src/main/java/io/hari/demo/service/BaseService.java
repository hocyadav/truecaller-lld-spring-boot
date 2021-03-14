package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;

import java.util.List;

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

}
