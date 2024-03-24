package ru.msu.cmc.prak.DAO;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Date.UTC;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class CommonDAOTest {

    @Autowired
    private ModelDAO modelDAO;

    @Autowired
    private BrandDAO brandDAO;

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private CarDAO carDAO;

    @Autowired
    private SessionFactory sessionFactory;

    private Brand b;
    private Model m;
    private Car c;
    private Client cl;
    private Order o;

    @Test
    void testGetAll() {
        List<Brand> brandList = brandDAO.getAll();
        assertEquals(4,brandList.size());

        List<Model> modelList = modelDAO.getAll();
        assertEquals(8, modelList.size());

        List<Car> carList = carDAO.getAll();
        assertEquals(10, carList.size());

        List<Client> clientList = clientDAO.getAll();
        assertEquals(6, clientList.size());

        List<Order> orderList = orderDAO.getAll();
        assertEquals(8, orderList.size());
    }

    @Test
    void testGetEntityById() {
        b = brandDAO.getEntityById(1);
        assertEquals(1, b.getId());
        assertEquals("LADA", b.getName());

        m = modelDAO.getEntityById(7);
        assertEquals(7, m.getId());
        assertEquals("Kalina", m.getName());
        assertEquals(b, m.getBrand());

        cl = clientDAO.getEntityById(4);
        assertEquals(4, cl.getId());
        assertEquals("iiivvv@yandex.ru", cl.getEmail());
        assertEquals("г. Москва, Мичуринский пр-кт 19к2, кв. 11 ", cl.getAddress());
        assertEquals("Иванов Иван Иванович", cl.getFullName());
        assertEquals(9261230051L, cl.getPhone());

        c = carDAO.getEntityById(4);
        assertEquals(4, c.getId());
        assertEquals(207000, c.getMileage());
        assertEquals(330000, c.getPrice());
        assertEquals(Drive_Type.front, c.getDrive());
        assertEquals(m, c.getModel());
        assertEquals(Fuel_Type.gas, c.getFuel());
        assertEquals(Transmission_Type.MT, c.getTransmission());
        assertEquals(1, c.getFeatures().size());
        assertEquals("Кондиционер", c.getFeatures().get(0));
        assertTrue(c.isLeft_steering_wheel());
        assertEquals(2012, c.getConfiguration().getYear());
        assertEquals("серебристый", c.getConfiguration().getColor());
        assertEquals(1600, c.getConfiguration().getEngine_capacity());
        assertEquals(5, c.getConfiguration().getNum_doors());
        assertEquals(89, c.getConfiguration().getEngine_power());
        assertEquals("хэтчбек", c.getConfiguration().getCar_body());
        assertNull(c.getConfiguration().getTrunk_volume());
        assertEquals(5, c.getConfiguration().getNum_seats());
        assertEquals("Россия", c.getConfiguration().getAssembly_country());

        o = orderDAO.getEntityById(8);
        assertEquals(8, o.getId());
        assertEquals(c, o.getCar());
        assertEquals(cl, o.getClient());
        assertEquals(Status_Type.issued, o.getStatus());
        assertFalse(o.isTest_drive());
        assertEquals(new Timestamp(new Date( UTC(2024 - 1900, Calendar.FEBRUARY, 20, 2, 20, 7)).getTime()), o.getDate());

        c = carDAO.getEntityById(0);
        o = orderDAO.getEntityById(0);
        cl = clientDAO.getEntityById(0);
        m = modelDAO.getEntityById(0);
        b = brandDAO.getEntityById(0);
        assertNull(c);
        assertNull(o);
        assertNull(cl);
        assertNull(m);
        assertNull(b);
    }

    @Test
    void testSaveAndDelete() {
        b = new Brand(null, "Test brand");
        m = new Model(null, b, "Test model");
        c = new Car(null, m, true, 0, 0, Transmission_Type.MT, Drive_Type.front, Fuel_Type.electricity, null, null);
        cl = new Client(null, "Test email", "Test address", "Test name", 0L);
        o = new Order(null, cl, new Timestamp(new Date(UTC(2024 - 1900, Calendar.JANUARY, 0, 0, 0, 0)).getTime()), Status_Type.canceled, c, false);
        int brandListInitSize = brandDAO.getAll().size();
        int modelListInitSize = modelDAO.getAll().size();
        int carListInitSize = carDAO.getAll().size();
        int clientListInitSize = clientDAO.getAll().size();
        int orderListInitSize = orderDAO.getAll().size();

        brandDAO.save(b);
        modelDAO.save(m);
        carDAO.save(c);
        clientDAO.save(cl);
        orderDAO.save(o);
        assertEquals(brandListInitSize + 1, brandDAO.getAll().size());
        assertEquals(modelListInitSize + 1, modelDAO.getAll().size());
        assertEquals(carListInitSize + 1, carDAO.getAll().size());
        assertEquals(clientListInitSize + 1, clientDAO.getAll().size());
        assertEquals(orderListInitSize + 1, orderDAO.getAll().size());
        assertEquals(b, brandDAO.getEntityById(b.getId()));
        assertEquals(m, modelDAO.getEntityById(m.getId()));
        assertEquals(c, carDAO.getEntityById(c.getId()));
        assertEquals(cl, clientDAO.getEntityById(cl.getId()));
        assertEquals(o, orderDAO.getEntityById(o.getId()));

        modelDAO.delete(m);
        assertEquals(modelListInitSize, modelDAO.getAll().size());
        assertNull(modelDAO.getEntityById(m.getId()));

        modelDAO.save(m);
        brandDAO.delete(b);
        assertEquals(brandListInitSize, brandDAO.getAll().size());
        assertNull(brandDAO.getEntityById(b.getId()));
        assertEquals(modelListInitSize, modelDAO.getAll().size());
        assertNull(modelDAO.getEntityById(m.getId()));

        c = carDAO.getEntityById(c.getId());
        assertNull(c.getModel());

        orderDAO.delete(o);
        assertEquals(orderListInitSize, orderDAO.getAll().size());
        assertNull(orderDAO.getEntityById(o.getId()));

        orderDAO.save(o);
        carDAO.delete(c.getId());
        assertEquals(orderListInitSize, orderDAO.getAll().size());
        assertNull(orderDAO.getEntityById(o.getId()));
        assertEquals(carListInitSize, carDAO.getAll().size());
        assertNull(carDAO.getEntityById(c.getId()));

        carDAO.save(c);
        o.setCar(c);
        orderDAO.save(o);
        clientDAO.delete(cl.getId());
        assertEquals(orderListInitSize, orderDAO.getAll().size());
        assertNull(orderDAO.getEntityById(o.getId()));
        assertEquals(clientListInitSize, clientDAO.getAll().size());
        assertNull(clientDAO.getEntityById(cl.getId()));

        carDAO.delete(c);
        assertEquals(carListInitSize, carDAO.getAll().size());
        assertNull(carDAO.getEntityById(c.getId()));

        b.setId(20);
        brandDAO.save(b);
        assertNotEquals(20, b.getId());

        brandDAO.delete(b.getId());
        assertEquals(brandListInitSize, brandDAO.getAll().size());
        assertNull(brandDAO.getEntityById(b.getId()));
        brandDAO.delete(null);
    }

    @Test
    void testUpdate() {
        b = new Brand(null, "Test brand");
        m = new Model(null, b, "Test model");
        c = new Car(null, m, true, 0, 0, Transmission_Type.MT, Drive_Type.front, Fuel_Type.electricity, null, null);
        brandDAO.save(b);
        modelDAO.save(m);
        carDAO.save(c);
        int carListInitSize = carDAO.getAll().size();
        int brandListInitSize = brandDAO.getAll().size();
        int modelListInitSize = modelDAO.getAll().size();

        c.setModel(m);
        Configuration conf = new Configuration(null, null, 2020,
                null, null, null,
                null, null, null);
        assertEquals(1, conf.getCurrentFields().size());
        assertEquals("year", conf.getCurrentFields().get(0));
        assertEquals(0, new Configuration().getCurrentFields().size());

        List<String> feat = new ArrayList<>();
        feat.add("Test feature");
        c.setConfiguration(conf);
        c.setFeatures(feat);
        c.setDrive(Drive_Type.four);
        Car car = carDAO.update(c);

        assertEquals(car, c);
        assertEquals(m, carDAO.getEntityById(c.getId()).getModel());
        assertEquals(conf, carDAO.getEntityById(c.getId()).getConfiguration());
        assertEquals(feat, carDAO.getEntityById(c.getId()).getFeatures());
        assertEquals(Drive_Type.four, carDAO.getEntityById(c.getId()).getDrive());
        assertEquals(carListInitSize, carDAO.getAll().size());

        carDAO.delete(car);
        brandDAO.delete(b);
        assertEquals(brandListInitSize - 1, brandDAO.getAll().size());
        assertEquals(modelListInitSize - 1, modelDAO.getAll().size());
        assertEquals(carListInitSize - 1, carDAO.getAll().size());

        car = carDAO.update(null);
        assertNull(car);
        assertEquals(carListInitSize - 1, carDAO.getAll().size());

        car = carDAO.update(c);
        assertNull(car);
    }
}

