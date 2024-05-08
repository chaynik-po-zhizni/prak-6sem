package ru.msu.cmc.prak.DAO.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prak.DAO.CarDAO;
import ru.msu.cmc.prak.entities.Car;
import ru.msu.cmc.prak.entities.Configuration;

import java.util.List;

@Repository
public class CarDAOImpl  extends CommonDAOImpl<Car> implements CarDAO {
    public CarDAOImpl() {
        super(Car.class);
    }

    @Override
    public List<Car> getByFilter(Filter filter) {
        try(Session session = sessionFactory.openSession()) {
            initializeQueryString("from Car as c left outer join c.model as m left outer join m.brand as b ");
            configureQueryStringMain(filter);
            configureQueryStringOptional(filter);
            //System.out.println(getQueryString());
            Query<Car> query = session.createQuery(getQueryString(), Car.class);
            if (filter.getBrand() != null) {
                query.setParameter("brandName", filter.getBrand());
            }
            if (filter.getModel() != null) {
                query.setParameter("modelName", filter.getModel());
            }
            if (filter.getPriceFrom() != null) {
                query.setParameter("priceFrom", filter.getPriceFrom());
            }
            if (filter.getPriceTo() != null) {
                query.setParameter("priceTo", filter.getPriceTo());
            }
            if (filter.getMileageFrom() != null) {
                query.setParameter("mileageFrom", filter.getMileageFrom());
            }
            if (filter.getMileageTo() != null) {
                query.setParameter("mileageTo", filter.getMileageTo());
            }
            if (filter.getColor() != null) {
                query.setParameter("color", filter.getColor());
            }
            if (filter.getNumSeats() != null) {
                query.setParameter("seats", filter.getNumSeats());
            }
            if (filter.getCarBody() != null) {
                query.setParameter("body", filter.getCarBody());
            }
            if (filter.getEnginePowerFrom() != null) {
                query.setParameter("enginePowerFrom", filter.getEnginePowerFrom());
            }
            if (filter.getEnginePowerTo() != null) {
                query.setParameter("enginePowerTo", filter.getEnginePowerTo());
            }
            if (filter.getCountry() != null) {
                query.setParameter("country", filter.getCountry());
            }
            if (filter.getYearFrom() != null) {
                query.setParameter("yearFrom", filter.getYearFrom());
            }
            if (filter.getYearTo() != null) {
                query.setParameter("yearTo", filter.getYearTo());
            }
            return query.getResultList();
        }
    }

    @Override
    public List<String> getValuesFiled(String field) {
        if (field == null) {
            return null;
        }
        if (!field.equals("num_seats")
                && !field.equals("car_body")
                && !field.equals("assembly_country")
                && !field.equals("color")) {
            return null;
        }
        try(Session session = sessionFactory.openSession()) {
            String queryString = "select distinct c.configuration." + field + " from Car as c ";
            initializeQueryString(queryString);
            Query<String> query = session.createQuery(getQueryString(), String.class);
            return query.getResultList();
        }
    }

    @Override
    public Integer getMinYearValue() {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "select distinct min(c.configuration.year) from Car as c ";
            initializeQueryString(queryString);
            Query<Integer> query = session.createQuery(getQueryString(), Integer.class);
            return query.uniqueResult();
        }
    }

    private void configureQueryStringMain(Filter filter) {
        if (filter.getBrand() != null) {
            setParameterToQueryString("b.name =: brandName");
        }
        if (filter.getModel() != null) {
            setParameterToQueryString("m.name =: modelName");
        }
        if (filter.getDriveType() != null) {
            setParameterToQueryString("c.drive = " + "'" + filter.getDriveType() + "'");
        }
        if (filter.getTransmissionType() != null) {
            setParameterToQueryString("c.transmission = " + "'" + filter.getTransmissionType() + "'");
        }
        if (filter.getFuelType() != null) {
            setParameterToQueryString("c.fuel = " + "'" + filter.getFuelType() + "'");
        }
        if (filter.getPriceFrom() != null) {
            setParameterToQueryString("c.price >= :priceFrom");
        }
        if (filter.getPriceTo() != null) {
            setParameterToQueryString("c.price <= :priceTo");
        }
        if (filter.getMileageFrom() != null) {
            setParameterToQueryString("c.mileage is not null");
            setParameterToQueryString("c.mileage >= :mileageFrom");
        }
        if (filter.getMileageTo() != null) {
            setParameterToQueryString("c.mileage <= :mileageTo");
        }
        if (filter.getIsLeftSteeringWheel() != null) {
            String t = filter.getIsLeftSteeringWheel()? "": "not ";
            setParameterToQueryString(t + "c.left_steering_wheel");
        }
    }
    private void configureQueryStringOptional(Filter filter) {
        if (filter.getColor() != null) {
            setParameterToQueryString("c.configuration.color = :color");
        }
        if (filter.getNumSeats() != null) {
            setParameterToQueryString("c.configuration.num_seats =: seats");
        }
        if (filter.getCarBody() != null) {
            setParameterToQueryString("c.configuration.car_body =: body");
        }
        if (filter.getEnginePowerFrom() != null) {
            setParameterToQueryString("c.configuration.engine_power is not null");
            setParameterToQueryString("c.configuration.engine_power >= : enginePowerFrom");
        }
        if (filter.getEnginePowerTo() != null) {
            setParameterToQueryString("c.configuration.engine_power <= : enginePowerTo");
        }
        if (filter.getCountry() != null) {
            setParameterToQueryString("c.configuration.assembly_country = : country");
        }
        if (filter.getYearFrom() != null) {
            setParameterToQueryString("c.configuration.year is not null");
            setParameterToQueryString("c.configuration.year >= : yearFrom");
        }
        if (filter.getYearTo() != null) {
            setParameterToQueryString("c.configuration.year <= : yearTo");
        }
    }
}
