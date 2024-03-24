
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class BrandDAOTest {

    @Autowired
    private BrandDAO brandDAO;

    @Autowired
    private SessionFactory sessionFactory;

    private Brand b;

    @Test
    void testGetBrandByName() {
        String brandName = b.getName();
        Brand brand = brandDAO.getBrandByName(brandName);
        //System.out.println(brand.getId()+ brand.getName());
        assertEquals(brand, b);
        brandName = null;
        brand = brandDAO.getBrandByName(brandName);
        assertNull(brand);
        brandName = "Test";
        brand = brandDAO.getBrandByName(brandName);
        assertNull(brand);
    }

    @BeforeAll
    void prepare() {
        b = new Brand(null, "Test brand");
        brandDAO.save(b);
    }

    @AfterAll
    void clean() {
        brandDAO.delete(b);
    }
}
