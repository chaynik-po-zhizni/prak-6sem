package ru.msu.cmc.prak.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.Car;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class NewCarAndCarAndCarsTest extends CommonTest {
    @Test
    void carsTest1() throws Exception {
        List<Car> allCars = carDAO.getAll();
        driver.findElement(By.linkText("Автомобили")).click();
        String testPrice = "-1";
        String newTestPrice = "-10";
        List<WebElement> exampls = driver.findElements(By.className("carShow"));
        assertEquals(allCars.size(), exampls.size());
        driver.findElement(By.linkText("Добавить новый автомобиль")).click();
        WebElement selectElement = driver.findElement(By.name("modelName"));
        Select select = new Select(selectElement);
        try {
            select.selectByVisibleText("Kalina");
        }
        catch (Exception ignored1) {
            selectElement = driver.findElement(By.name("brandName"));
            select = new Select(selectElement);
            select.selectByValue("LADA");
            try {
                selectElement = driver.findElement(By.name("modelName"));
                select = new Select(selectElement);
                select.selectByValue("Cube");
            }
            catch (Exception ignored2) {
                select.selectByValue("Kalina");
                driver.findElement(By.name("price")).sendKeys(testPrice);
                WebElement element = driver.findElement(By.className("btn-success"));
                driver.executeScript("arguments[0].click();", element);
                wait100();

                assertEquals(testPrice, driver.findElement(By.id("priceInput")).getAttribute("value"));
                selectElement = driver.findElement(By.name("brandName"));
                select = new Select(selectElement);
                select.selectByValue("Nissan");
                try {
                    selectElement = driver.findElement(By.name("modelName"));
                    select = new Select(selectElement);
                    select.selectByValue("Kalina");
                }
                catch (Exception ignored3) {
                    select.selectByValue("Cube");
                    driver.findElement(By.name("price")).sendKeys("0");
                    element = driver.findElement(By.id("editButton"));
                    driver.executeScript("arguments[0].click();", element);
                    wait100();
                    assertEquals(newTestPrice, driver.findElement(By.id("priceInput")).getAttribute("value"));
                    driver.findElement(By.linkText("Автомобили")).click();
                    exampls = driver.findElements(By.className("carShow"));
                    assertEquals(allCars.size() + 1, exampls.size());
                    driver.findElement(By.name("priceFrom")).sendKeys(newTestPrice);
                    driver.findElement(By.name("priceTo")).sendKeys(newTestPrice);
                    element = driver.findElement(By.id("findCars"));
                    driver.executeScript("arguments[0].click();", element);
                    wait100();
                    exampls = driver.findElements(By.className("carShow"));
                    assertEquals( 1, exampls.size());
                    assertEquals(newTestPrice, exampls.get(0).findElement(By.id("priceShow")).getText());
                    element = driver.findElement(By.id("infoButton"));
                    driver.executeScript("arguments[0].click();", element);
                    wait100();

                    element =  driver.findElement(By.className("btn-danger"));
                    driver.executeScript("arguments[0].click();", element);
                    wait100();
                    exampls = driver.findElements(By.className("carShow"));
                    assertEquals(allCars.size(), exampls.size());
                    return;
                }
                throw new Exception("Test failed because Kalina but Nissan was clicked");
            }
            throw new Exception("Test failed because Cube but LADA was clicked");
        }
        throw new Exception("Test failed because Kalina but not LADA was clicked");
    }

    @Test
    void carsTest2() {
        driver.findElement(By.linkText("Автомобили")).click();
        WebElement selectElement = driver.findElement(By.name("order"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Непроданная");
        WebElement element = driver.findElement(By.id("findCars"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();
        List<WebElement> exampls = driver.findElements(By.className("carShow"));
        assertEquals(2, exampls.size());
        element = driver.findElement(By.id("infoButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();

        element =  driver.findElement(By.className("btn-warning"));
        driver.executeScript("arguments[0].click();", element);
        wait100();

        assertEquals(rootUrl + "newOrder?carId=9",
                driver.getCurrentUrl());
    }
    @Test
    void carsTest3() {
        driver.findElement(By.linkText("Автомобили")).click();
        WebElement selectElement = driver.findElement(By.name("order"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Проданная");
        WebElement element = driver.findElement(By.id("findCars"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();
        List<WebElement> exampls = driver.findElements(By.className("carShow"));
        assertEquals(8, exampls.size());

        element = driver.findElement(By.id("infoButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();

        element =  driver.findElement(By.className("btn-info"));
        driver.executeScript("arguments[0].click();", element);
        wait100();

        assertEquals(rootUrl + "orderCar?id=4",
                driver.getCurrentUrl());
    }

}
