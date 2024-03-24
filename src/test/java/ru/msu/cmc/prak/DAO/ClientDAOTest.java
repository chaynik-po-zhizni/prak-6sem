package ru.msu.cmc.prak.DAO;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.Client;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class ClientDAOTest {
    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private SessionFactory sessionFactory;

    private Client c;

    @Test
    void testGetClintByFilter() {
        ClientDAO.Filter filter = new ClientDAO.Filter(1234567L, "Test email", "Doe John");
        List<Client> clientList = clientDAO.getByFilter(filter);
        assertEquals(1, clientList.size());
        assertEquals(c, clientList.get(0));

        filter = new ClientDAO.Filter(1234567L, null, null);
        clientList = clientDAO.getByFilter(filter);
        assertEquals(1, clientList.size());
        assertEquals(c, clientList.get(0));

        filter = new ClientDAO.Filter(null, "Test email", null);
        clientList = clientDAO.getByFilter(filter);
        assertEquals(1, clientList.size());
        assertEquals(c, clientList.get(0));

        filter = new ClientDAO.Filter(null, null, "Doe");
        clientList = clientDAO.getByFilter(filter);
        assertEquals(1, clientList.size());
        assertEquals(c, clientList.get(0));

        filter = new ClientDAO.Filter();
        clientList = clientDAO.getByFilter(filter);
        assertEquals(clientList, clientDAO.getAll());

        filter.setName("");
        clientList = clientDAO.getByFilter(filter);
        assertEquals(clientList, clientDAO.getAll());

        filter = new ClientDAO.Filter();
        filter.setPhone(0L);
        clientList = clientDAO.getByFilter(filter);
        assertEquals(0, clientList.size());

        filter = new ClientDAO.Filter();
        filter.setEmail("Test");
        clientList = clientDAO.getByFilter(filter);
        assertEquals(0, clientList.size());

        filter = new ClientDAO.Filter();
        filter.setName("\n\n\n");
        clientList = clientDAO.getByFilter(filter);
        assertEquals(0, clientList.size());

    }

    @BeforeAll
    void prepare() {
        c = new Client(null, "Test email", "Test address", "Doe John", 1234567);
        clientDAO.save(c);
    }

    @AfterAll
    void clean() {
        clientDAO.delete(c);
    }
}
