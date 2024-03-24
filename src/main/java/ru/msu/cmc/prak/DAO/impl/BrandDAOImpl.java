package ru.msu.cmc.prak.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prak.DAO.BrandDAO;
import ru.msu.cmc.prak.entities.Brand;

@Repository
public class BrandDAOImpl extends CommonDAOImpl<Brand> implements BrandDAO {
    public BrandDAOImpl() {
        super(Brand.class);
    }

    @Override
    public Brand getBrandByName(String name) {
        if (name == null) {
            return null;
        }
        try(Session session = sessionFactory.openSession()) {
            initializeQueryString("from Brand as b");
            setParameterToQueryString("b.name =: name");
            Query<Brand> query = session.createQuery(getQueryString(), Brand.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }
    }
}
