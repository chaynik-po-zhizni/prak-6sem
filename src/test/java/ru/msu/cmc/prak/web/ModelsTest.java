package ru.msu.cmc.prak.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class ModelsTest extends CommonTest {
    @Test
    void modelsTest1() {
        String newTestName = "TEST NAME NEW";
        String testName = "TEST NAME";
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

        List<WebElement> exampls = driver.findElements(By.className("tableText"));
        List<Model> models = modelDAO.getModelsByBrand("LADA");
        assertEquals(models.size(), exampls.size());
        for (int i = 0; i < models.size(); i++)
            assertEquals(models.get(i).getName(), exampls.get(i).getText());
        driver.findElement(By.name("name")).sendKeys(testName);
        driver.findElement(By.id("addNewModel")).click();

        exampls = driver.findElements(By.className("tableText"));
        assertEquals(models.size() + 1, exampls.size());
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
        assertEquals(models.size() + 1, exampls.size());
        flag = false;
        for (WebElement cell : exampls)
            if (cell.getText().equals(newTestName)) {
                flag = true;
                break;
            }
        assertTrue(flag);

        rows = driver.findElements(By.className("tableModels"));
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
        assertEquals(models.size(), exampls.size());
    }

    @Test
    void modelsTest2() {
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

        rows = driver.findElements(By.className("tableModels"));
        for (WebElement row : rows) {
            String authorName = row.findElement(By.className("tableText")).getText();
            if (authorName.equals("Kalina")) {
                WebElement element = row.findElement(By.id("carsbutton"));
                driver.executeScript("arguments[0].click();", element);
                break;
            }
        }
        wait100();
        assertEquals(rootUrl + "cars?brand=LADA&mod=Kalina",
                driver.getCurrentUrl());
    }
    @Test
    void modelsTest3() {
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

        driver.findElement(By.id("findName")).sendKeys("Kalina");
        driver.findElement(By.id("findModels")).click();
        wait100();
        assertEquals(rootUrl + "models?brandName=LADA&name=Kalina",
                driver.getCurrentUrl());
        List<WebElement> exampls = driver.findElements(By.className("tableText"));
        assertEquals(1, exampls.size());
        rows = driver.findElements(By.className("tableModels"));
        String brandName = rows.get(0).findElement(By.className("tableText")).getText();
        assertEquals("Kalina", brandName);
    }

    @Test
    void modelsTestError() {
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
        driver.findElement(By.id("changebrand")).click();
        driver.findElement(By.name("oldName")).sendKeys("Vesta");
        driver.findElement(By.name("newName")).sendKeys("Kalina");
        driver.findElement(By.id("save")).click();
        assertEquals(rootUrl + "models/edit",
                driver.getCurrentUrl());
    }
}
