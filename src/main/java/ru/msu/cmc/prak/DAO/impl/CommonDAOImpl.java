package ru.msu.cmc.prak.DAO.impl;

import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msu.cmc.prak.DAO.CommonDAO;
import ru.msu.cmc.prak.entities.CommonEntity;

import java.util.List;
@Repository
public abstract class CommonDAOImpl <E extends CommonEntity> implements CommonDAO <E>{

    protected SessionFactory sessionFactory;

    protected Class <E> entityClass;

    public CommonDAOImpl(Class <E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
        this.sessionFactory = sessionFactory.getObject();
    }

    @Override
    public List<E> getAll() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaQuery<E> criteriaQuery = session.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.from(entityClass);
            Query<E> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    @Override
    public E getEntityById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    @Override
    public E update(E entity) {
        if (entity == null) {
            return null;
        }
        if (this.getEntityById(entity.getId()) != null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                E res = session.merge(entity);
                transaction.commit();
                return res;
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        E entity = this.getEntityById(id);
        this.delete(entity);
    }

    @Override
    public void delete(E entity) {
        if (entity == null)
            return;
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
    }

    @Override
    public void save(E entity) {
        if (entity == null)
            return;
        try(Session session = sessionFactory.openSession()) {
            if (null != entity.getId()) {
                entity.setId(null);
            }
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
    }

    private boolean isWritten;
    private String queryString;
    protected void initializeQueryString(String initString) {
        queryString = initString + " ";
        isWritten = false;
    }
    protected void setParameterToQueryString(String parameterToQueryString) {
        queryString += isWritten ? "and " : "where ";
        queryString += parameterToQueryString + " ";
        isWritten = true;
    }
    protected String getQueryString() {
        return queryString;
    }
}
