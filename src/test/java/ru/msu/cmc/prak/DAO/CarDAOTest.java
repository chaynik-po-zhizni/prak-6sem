package ru.msu.cmc.prak.DAO;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class CarDAOTest {
    @Autowired
    private CarDAO carDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testGetCarByFilter() {
        CarDAO.Filter filter = new CarDAO.Filter();
        List<Car> carList = carDAO.getByFilter(filter);
        assertEquals(carDAO.getAll(), carList);

        filter = new CarDAO.Filter();
        filter.setDriveType(Drive_Type.four);
        carList = carDAO.getByFilter(filter);
        assertEquals(1, carList.size());

        filter = new CarDAO.Filter();
        filter.setTransmissionType(Transmission_Type.Robot);
        carList = carDAO.getByFilter(filter);
        assertEquals(1, carList.size());

        filter = new CarDAO.Filter();
        filter.setFuelType(Fuel_Type.electricity);
        carList = carDAO.getByFilter(filter);
        assertEquals(1, carList.size());

        filter = new CarDAO.Filter();
        filter.setPriceFrom(330000);
        carList = carDAO.getByFilter(filter);
        assertEquals(5, carList.size());

        filter = new CarDAO.Filter();
        filter.setPriceTo(330000);
        carList = carDAO.getByFilter(filter);
        assertEquals(6, carList.size());

        filter.setPriceFrom(330000);
        carList = carDAO.getByFilter(filter);
        assertEquals(1, carList.size());

        filter = new CarDAO.Filter();
        filter.setMileageFrom(230000);
        carList = carDAO.getByFilter(filter);
        assertEquals(2, carList.size());

        filter = new CarDAO.Filter();
        filter.setMileageTo(230000);
        carList = carDAO.getByFilter(filter);
        assertEquals(9, carList.size());

        filter.setMileageFrom(207000);
        carList = carDAO.getByFilter(filter);
        assertEquals(3, carList.size());

        filter = new CarDAO.Filter();
        filter.setIsLeftSteeringWheel(false);
        carList = carDAO.getByFilter(filter);
        assertEquals(2, carList.size());

        filter = new CarDAO.Filter();
        filter.setColor("белый");
        carList = carDAO.getByFilter(filter);
        assertEquals(2, carList.size());

        filter = new CarDAO.Filter();
        filter.setCarBody("седан");
        carList = carDAO.getByFilter(filter);
        assertEquals(4, carList.size());

        filter = new CarDAO.Filter();
        filter.setEnginePowerFrom(100);
        carList = carDAO.getByFilter(filter);
        assertEquals(4, carList.size());

        filter = new CarDAO.Filter();
        filter.setEnginePowerTo(100);
        carList = carDAO.getByFilter(filter);
        assertEquals(6, carList.size());

        filter.setEnginePowerFrom(80);
        carList = carDAO.getByFilter(filter);
        assertEquals(4, carList.size());

        filter = new CarDAO.Filter();
        filter.setCountry("Россия");
        carList = carDAO.getByFilter(filter);
        assertEquals(5, carList.size());

        filter = new CarDAO.Filter();
        filter.setYearFrom(2000);
        carList = carDAO.getByFilter(filter);
        assertEquals(9, carList.size());

        filter = new CarDAO.Filter();
        filter.setYearTo(2000);
        carList = carDAO.getByFilter(filter);
        assertEquals(1, carList.size());

        filter = new CarDAO.Filter(null, null, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null, 2010, 2015);
        carList = carDAO.getByFilter(filter);
        assertEquals(4, carList.size());

        filter = new CarDAO.Filter();
        filter.setNumSeats(4);
        carList = carDAO.getByFilter(filter);
        assertEquals(1, carList.size());

        filter = new CarDAO.Filter();
        filter.setModel("Kalina");
        carList = carDAO.getByFilter(filter);
        assertEquals(2, carList.size());

        filter = new CarDAO.Filter();
        filter.setBrand("LADA");
        carList = carDAO.getByFilter(filter);
        assertEquals(5, carList.size());

        filter = new CarDAO.Filter();
        filter.setModel("Test model");
        carList = carDAO.getByFilter(filter);
        assertEquals(0, carList.size());
    }

    @Test
    void testGetValues() {
        List<String> res = carDAO.getValuesFiled("car_body");
        assertEquals(4, res.size());

        res = carDAO.getValuesFiled("num_seats");
        assertEquals(3, res.size());

        res = carDAO.getValuesFiled("assembly_country");
        assertEquals(5, res.size());

        res = carDAO.getValuesFiled("color");
        assertEquals(7, res.size());

        res = carDAO.getValuesFiled("Test");
        assertNull(res);

        res = carDAO.getValuesFiled(null);
        assertNull(res);
    }

    @Test
    void testGetMinYearValue() {
        Integer res = carDAO.getMinYearValue();
        assertEquals(1996, res);
    }

}

