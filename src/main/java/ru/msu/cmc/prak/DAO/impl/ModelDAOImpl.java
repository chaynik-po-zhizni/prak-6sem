package ru.msu.cmc.prak.DAO.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prak.DAO.ModelDAO;
import ru.msu.cmc.prak.entities.Brand;
import ru.msu.cmc.prak.entities.Model;

import java.util.List;

@Repository
public class ModelDAOImpl extends CommonDAOImpl<Model> implements ModelDAO {
    public ModelDAOImpl() {
        super(Model.class);
    }

    @Override
    public List<Model> getModelsByBrand(String brandName) {
        if (brandName == null) {
            return null;
        }
        try(Session session = sessionFactory.openSession()) {
            initializeQueryString("from Model as m join m.brand as b");
            setParameterToQueryString("b.name =: brand_name");
            Query<Model> query = session.createQuery(getQueryString(), Model.class);
            query.setParameter("brand_name", brandName);
            return query.getResultList();
        }
    }

    @Override
    public Model getModelByName(String brandName, String name) {
        if (brandName == null || name == null) {
            return null;
        }
        try(Session session = sessionFactory.openSession()) {
            initializeQueryString("from Model as m join m.brand as b");
            setParameterToQueryString("m.name =: name");
            setParameterToQueryString("b.name =: brand_name");
            Query<Model> query = session.createQuery(getQueryString(), Model.class);
            query.setParameter("name", name);
            query.setParameter("brand_name", brandName);
            return query.uniqueResult();
        }
    }
}
