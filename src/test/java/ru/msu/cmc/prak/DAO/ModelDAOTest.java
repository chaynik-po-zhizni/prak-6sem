package ru.msu.cmc.prak.DAO;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.Brand;
import ru.msu.cmc.prak.entities.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class ModelDAOTest {

    @Autowired
    private ModelDAO modelDAO;

    @Autowired
    private BrandDAO brandDAO;

    @Autowired
    private SessionFactory sessionFactory;

    private Brand b;
    private Model m;

    @Test
    void testGetModelByName() {
        String name = m.getName();
        String brandName = m.getBrand().getName();
        assertEquals(m.getBrand(), b);
        assertEquals(brandName, b.getName());

        Model model = modelDAO.getModelByName(brandName, name);
        assertEquals(model, m);

        name = null;
        model = modelDAO.getModelByName(name, brandName);
        assertNull(model);

        brandName = null;
        model = modelDAO.getModelByName(name, brandName);
        assertNull(model);

        name = m.getName();
        model = modelDAO.getModelByName(name, brandName);
        assertNull(model);

        name = "Test";
        brandName = "Test";
        model = modelDAO.getModelByName(name, brandName);
        assertNull(model);

        name = m.getName();
        model = modelDAO.getModelByName(name, brandName);
        assertNull(model);

        name = brandName;
        brandName = m.getBrand().getName();
        model = modelDAO.getModelByName(name, brandName);
        assertNull(model);
    }

    @Test
    void testGetModelsByBrand() {
        List<Model> models = modelDAO.getModelsByBrand(b.getName());
        assertEquals(1, models.size());
        assertEquals(m, models.get(0));

        models = modelDAO.getModelsByBrand("Test");
        assertEquals(0, models.size());

        models = modelDAO.getModelsByBrand("LADA");
        assertEquals(4, models.size());

        models = modelDAO.getModelsByBrand(null);
        assertNull(models);
    }

    @BeforeAll
    void prepare() {
        b = new Brand(null, "Test brand");
        m = new Model(null, b, "Test Model");
        brandDAO.save(b);
        modelDAO.save(m);
    }

    @AfterAll
    void clean() {
        int t = m.getId();
        brandDAO.delete(b);
        assertNull(modelDAO.getEntityById(t));
    }

}
