package ru.msu.cmc.prak.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prak.DAO.OrderDAO;
import ru.msu.cmc.prak.entities.Client;
import ru.msu.cmc.prak.entities.Order;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDAOImpl extends CommonDAOImpl<Order> implements OrderDAO {

    public OrderDAOImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            initializeQueryString("from Order as o join o.client as c");
            if (filter.getPhone() != null) {
                setParameterToQueryString("c.phone =: phone");
            }
            if (filter.getIsNeedTestDrive() != null) {
                String t = filter.getIsNeedTestDrive()? "": "not ";
                setParameterToQueryString("o.test_drive");
            }
            if (filter.getStatus() != null) {
                setParameterToQueryString("o.status = " + "'" + filter.getStatus() + "'");
            }
            if (filter.getDateTo() != null) {
                setParameterToQueryString("o.date <=: dateTo");
            }
            if (filter.getDateFrom() != null) {
                setParameterToQueryString("o.date >=: dateFrom");
            }
            //System.out.println(getQueryString());
            Query<Order> query = session.createQuery(getQueryString(), Order.class);
            if (filter.getPhone() != null) {
                query.setParameter("phone", filter.getPhone());
            }
            if (filter.getDateTo() != null) {
                Timestamp t = new Timestamp(filter.getDateTo().getTime());
                query.setParameter("dateTo", t);
            }
            if (filter.getDateFrom() != null) {
                Timestamp t = new Timestamp(filter.getDateFrom().getTime());
                query.setParameter("dateFrom", t);
            }
            return query.getResultList();
        }
    }
}
