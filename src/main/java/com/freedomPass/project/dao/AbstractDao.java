package com.freedomPass.project.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    Environment environment;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    protected T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    protected void persist(T entity) {
        getSession().persist(entity);
    }

    protected void merge(T entity) {
        getSession().merge(entity);
    }

    protected void save(T entity) {
        getSession().save(entity);
    }

    protected void update(T entity) {
        getSession().update(entity);
    }

    protected void delete(T entity) {
        getSession().delete(entity);
    }

    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass);
    }

    protected SQLQuery createSqlQuery(String query) {
        return getSession().createSQLQuery(query);
    }

    protected Query createHqlQuery(String query) {
        return getSession().createQuery(query);
    }

    protected Connection getDBConnection() {
        return ((SessionImpl) getSession()).connection();
    }

}
