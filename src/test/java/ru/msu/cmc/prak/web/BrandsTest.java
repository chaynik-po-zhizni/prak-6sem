package ru.msu.cmc.prak.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.Brand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class BrandsTest extends CommonTest {
    @Test
    void brandsTest1() {
        String newTestName = "TEST NAME NEW";
        String testName = "TEST NAME";
        driver.findElement(By.linkText("Бренды")).click();
        List<WebElement> exampls = driver.findElements(By.className("tableText"));
        List<Brand> brands = brandDAO.getAll();
        assertEquals(brands.size(), exampls.size());
        for (int i = 0; i < brands.size(); i++)
            assertEquals(brands.get(i).getName(), exampls.get(i).getText());
        driver.findElement(By.name("name")).sendKeys(testName);
        driver.findElement(By.id("addNewBrand")).click();

        exampls = driver.findElements(By.className("tableText"));
        assertEquals(brands.size() + 1, exampls.size());
        boolean flag = false;
        for (WebElement cell : exampls)
            if (cell.getText().equals(testName)) {
                flag = true;
                break;
            }
        assertTrue(flag);
        driver.findElement(By.id("changebrand")).click();
        driver.findElement(By.name("oldName")).sendKeys(testName);
        driver.findElement(By.name("newName")).sendKeys(newTestName);
        driver.findElement(By.id("save")).click();
        WebElement element = driver.findElement(By.className("cancel"));
        driver.executeScript("arguments[0].click();", element);
        wait100();

        exampls = driver.findElements(By.className("tableText"));
        assertEquals(brands.size() + 1, exampls.size());
        flag = false;
        for (WebElement cell : exampls)
            if (cell.getText().equals(newTestName)) {
                flag = true;
                break;
            }
        assertTrue(flag);


        List<WebElement> rows = driver.findElements(By.className("tableBrangs"));
        for (WebElement row : rows) {
            String authorName = row.findElement(By.className("tableText")).getText();
            if (authorName.equals(newTestName)) {
                element = row.findElement(By.className("btn-danger"));
                driver.executeScript("arguments[0].click();", element);
                break;
            }
        }
        wait100();
        exampls = driver.findElements(By.className("tableText"));
        assertEquals(brands.size(), exampls.size());
    }
    @Test
    void brandsTest2() {
        driver.findElement(By.linkText("Бренды")).click();
        List<WebElement> rows = driver.findElements(By.className("tableBrangs"));
        for (WebElement row : rows) {
            String authorName = row.findElement(By.className("tableText")).getText();
            if (authorName.equals("LADA")) {
                WebElement element = row.findElement(By.id("modelsbutton"));
                driver.executeScript("arguments[0].click();", element);
                break;
            }
        }
        wait100();
        assertEquals(rootUrl + "models?brandName=LADA",
                driver.getCurrentUrl());
    }
    @Test
    void brandsTest3() {
        driver.findElement(By.linkText("Бренды")).click();
        List<WebElement> rows = driver.findElements(By.className("tableBrangs"));
        for (WebElement row : rows) {
            String authorName = row.findElement(By.className("tableText")).getText();
            if (authorName.equals("LADA")) {
                WebElement element = row.findElement(By.id("carsbutton"));
                driver.executeScript("arguments[0].click();", element);
                break;
            }
        }
        wait100();
        assertEquals(rootUrl + "cars?brand=LADA",
                driver.getCurrentUrl());
    }
    @Test
    void brandsTest4() {
        driver.findElement(By.linkText("Бренды")).click();
        driver.findElement(By.id("findName")).sendKeys("LADA");
        driver.findElement(By.id("findBrands")).click();
        wait100();
        assertEquals(rootUrl + "brands?name=LADA",
                driver.getCurrentUrl());
        List<WebElement> exampls = driver.findElements(By.className("tableText"));
        assertEquals(1, exampls.size());
        List<WebElement> rows = driver.findElements(By.className("tableBrangs"));
        String brandName = rows.get(0).findElement(By.className("tableText")).getText();
        assertEquals("LADA", brandName);
    }

    @Test
    void brandsTestError() {
        driver.findElement(By.linkText("Бренды")).click();
        driver.findElement(By.id("changebrand")).click();
        driver.findElement(By.name("oldName")).sendKeys("Tesla");
        driver.findElement(By.name("newName")).sendKeys("LADA");
        driver.findElement(By.id("save")).click();
        assertEquals(rootUrl + "brands/edit",
                driver.getCurrentUrl());
    }
}
