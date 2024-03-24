package ru.msu.cmc.prak.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prak.DAO.ClientDAO;
import ru.msu.cmc.prak.entities.Client;

import java.util.List;

@Repository
public class ClientDAOImpl extends CommonDAOImpl<Client> implements ClientDAO {
    public ClientDAOImpl() {
        super(Client.class);
    }

    @Override
    public List<Client> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            initializeQueryString("from Client as c ");
            if (filter.getPhone() != null) {
                setParameterToQueryString("c.phone =: phone");
            }
            if (filter.getName() != null) {
                setParameterToQueryString("c.fullName LIKE CONCAT('%',:name,'%')");
            }
            if (filter.getEmail() != null) {
                setParameterToQueryString("c.email =: email");
            }
            Query<Client> query = session.createQuery(getQueryString(), Client.class);
            if (filter.getName() != null) {
                query.setParameter("name", filter.getName());
            }
            if (filter.getEmail() != null) {
                query.setParameter("email", filter.getEmail());
            }
            if (filter.getPhone() != null) {
                query.setParameter("phone", filter.getPhone());
            }
            return query.getResultList();
        }
    }
}
