package ru.msu.cmc.prak.DAO;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.Car;
import ru.msu.cmc.prak.entities.Client;
import ru.msu.cmc.prak.entities.Order;
import ru.msu.cmc.prak.entities.Status_Type;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Date.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class OrderDAOTest {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testGetOrderByFilter() {
        OrderDAO.Filter filter = new OrderDAO.Filter();
        List<Order> orderList = orderDAO.getByFilter(filter);
        assertEquals(orderDAO.getAll(), orderList);

        filter = new OrderDAO.Filter();
        filter.setStatus(Status_Type.done);
        orderList = orderDAO.getByFilter(filter);
        assertEquals(3, orderList.size());

        filter = new OrderDAO.Filter();
        filter.setPhone(9261230051L);
        orderList = orderDAO.getByFilter(filter);
        assertEquals(3, orderList.size());

        filter = new OrderDAO.Filter();
        filter.setIsNeedTestDrive(false);
        orderList = orderDAO.getByFilter(filter);
        assertEquals(4, orderList.size());

        filter = new OrderDAO.Filter();
        filter.setDateTo(new Date(2023 - 1900, Calendar.JANUARY, 1));
        orderList = orderDAO.getByFilter(filter);
        assertEquals(1, orderList.size());

        filter = new OrderDAO.Filter();
        filter.setDateFrom(new Date(2024 - 1900, Calendar.JANUARY, 1));
        orderList = orderDAO.getByFilter(filter);
        assertEquals(6, orderList.size());

        filter = new OrderDAO.Filter(null, null, null,
                new Date(2024 - 1900, Calendar.JANUARY, 20),
                new Date(2024 - 1900, Calendar.FEBRUARY, 1));
        orderList = orderDAO.getByFilter(filter);
        assertEquals(2, orderList.size());

        filter = new OrderDAO.Filter(Status_Type.done, 9261230051L, true,
                new Date(UTC(2023 - 1900, Calendar.APRIL, 30, 3, 45, 50)),
                new Date(UTC(2023 - 1900, Calendar.APRIL, 30, 3, 45, 50)));
        orderList = orderDAO.getByFilter(filter);
        assertEquals(1, orderList.size());
        assertEquals(orderDAO.getEntityById(1), orderList.get(0));

        filter = new OrderDAO.Filter(null, 0L, null, null, null);
        orderList = orderDAO.getByFilter(filter);
        assertEquals(0, orderList.size());
    }
}