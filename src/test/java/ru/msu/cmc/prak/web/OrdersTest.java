package ru.msu.cmc.prak.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class OrdersTest extends CommonTest {

    @Test
    void ordersTest1() {
        List<Order> allOrders = orderDAO.getAll();
        driver.findElement(By.linkText("Автомобили")).click();
        WebElement selectElement = driver.findElement(By.name("order"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Непроданная");
        WebElement element = driver.findElement(By.id("findCars"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();
        element = driver.findElement(By.id("infoButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        element = driver.findElement(By.className("btn-warning"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        String testPhone = "0000000000";
        String testName = "Test Name";
        driver.findElement(By.name("phone")).sendKeys(testPhone);

        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        select.selectByVisibleText("В процессе");
        wait100();

        selectElement = driver.findElement(By.name("test_drive"));
        select = new Select(selectElement);
        select.selectByVisibleText("Да");
        wait100();
        element = driver.findElement(By.className("btn-success"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();

        driver.findElement(By.name("name")).sendKeys(testName);
        element = driver.findElement(By.className("btn-success"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();

        assertEquals(testPhone, driver.findElement(By.id("phoneShow")).getText());
        assertEquals(testName, driver.findElement(By.id("nameShow")).getText());
        assertEquals("LADA", driver.findElement(By.id("brandShow")).getText());
        assertEquals("Kalina", driver.findElement(By.id("modelShow")).getText());
        assertEquals("359000", driver.findElement(By.id("priceShow")).getText());
        assertEquals("166000", driver.findElement(By.id("mileageShow")).getText());
        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        assertEquals("В процессе", select.getFirstSelectedOption().getText());
        selectElement = driver.findElement(By.name("test_drive"));
        select = new Select(selectElement);
        assertEquals("Да", select.getFirstSelectedOption().getText());
        wait100();

        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        select.selectByVisibleText("Готов");
        wait100();
        element = driver.findElement(By.id("editButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();
        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        assertEquals("Готов", select.getFirstSelectedOption().getText());

        driver.findElement(By.linkText("Заказы")).click();
        List<WebElement> exampls = driver.findElements(By.className("orderShow"));
        assertEquals(allOrders.size() + 1, exampls.size());
        driver.findElement(By.name("phone")).sendKeys(testPhone);
        driver.findElement(By.id("findOrders")).click();
        exampls = driver.findElements(By.className("orderShow"));
        assertEquals(1, exampls.size());
        assertEquals(testPhone, driver.findElement(By.id("phoneShow")).getText());
        assertEquals(testName, driver.findElement(By.id("nameShow")).getText());
        assertEquals("LADA", driver.findElement(By.id("brandShow")).getText());
        assertEquals("Kalina", driver.findElement(By.id("modelShow")).getText());
        element = driver.findElement(By.id("infoButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        assertEquals(testPhone, driver.findElement(By.id("phoneShow")).getText());
        assertEquals(testName, driver.findElement(By.id("nameShow")).getText());
        assertEquals("LADA", driver.findElement(By.id("brandShow")).getText());
        assertEquals("Kalina", driver.findElement(By.id("modelShow")).getText());
        assertEquals("359000", driver.findElement(By.id("priceShow")).getText());
        assertEquals("166000", driver.findElement(By.id("mileageShow")).getText());
        element = driver.findElement(By.className("btn-danger"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        exampls = driver.findElements(By.className("orderShow"));
        assertEquals(allOrders.size(), exampls.size());

        driver.findElement(By.linkText("Клиенты")).click();
        driver.findElement(By.name("phone")).sendKeys(testPhone);
        driver.findElement(By.id("findClients")).click();
        wait100();
        driver.findElement(By.id("infoButton")).click();
        wait100();
        element = driver.findElement(By.className("btn-danger"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
    }

    @Test
    void ordersTest2() {
        List<Order> allOrders = orderDAO.getAll();
        driver.findElement(By.linkText("Автомобили")).click();
        WebElement selectElement = driver.findElement(By.name("order"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Непроданная");
        WebElement element = driver.findElement(By.id("findCars"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();
        element = driver.findElement(By.id("infoButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        element = driver.findElement(By.className("btn-warning"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        String testPhone = "9270868596";
        driver.findElement(By.name("phone")).sendKeys(testPhone);

        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        select.selectByVisibleText("В процессе");
        wait100();

        selectElement = driver.findElement(By.name("test_drive"));
        select = new Select(selectElement);
        select.selectByVisibleText("Да");
        wait100();
        element = driver.findElement(By.className("btn-success"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();

        String testName = "Романов Валерий Андреевич";
        assertEquals(testPhone, driver.findElement(By.id("phoneShow")).getText());
        assertEquals(testName, driver.findElement(By.id("nameShow")).getText());
        assertEquals("LADA", driver.findElement(By.id("brandShow")).getText());
        assertEquals("Kalina", driver.findElement(By.id("modelShow")).getText());
        assertEquals("359000", driver.findElement(By.id("priceShow")).getText());
        assertEquals("166000", driver.findElement(By.id("mileageShow")).getText());
        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        assertEquals("В процессе", select.getFirstSelectedOption().getText());
        selectElement = driver.findElement(By.name("test_drive"));
        select = new Select(selectElement);
        assertEquals("Да", select.getFirstSelectedOption().getText());
        wait100();

        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        select.selectByVisibleText("Готов");
        wait100();
        element = driver.findElement(By.id("editButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();
        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        assertEquals("Готов", select.getFirstSelectedOption().getText());

        driver.findElement(By.linkText("Заказы")).click();
        List<WebElement> exampls = driver.findElements(By.className("orderShow"));
        assertEquals(allOrders.size() + 1, exampls.size());
        driver.findElement(By.name("phone")).sendKeys(testPhone);
        selectElement = driver.findElement(By.name("status"));
        select = new Select(selectElement);
        select.selectByVisibleText("Готов");
        driver.findElement(By.id("findOrders")).click();
        assertEquals(testPhone, driver.findElement(By.id("phoneShow")).getText());
        assertEquals(testName, driver.findElement(By.id("nameShow")).getText());
        assertEquals("LADA", driver.findElement(By.id("brandShow")).getText());
        assertEquals("Kalina", driver.findElement(By.id("modelShow")).getText());
        element = driver.findElement(By.id("infoButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        assertEquals(testPhone, driver.findElement(By.id("phoneShow")).getText());
        assertEquals(testName, driver.findElement(By.id("nameShow")).getText());
        assertEquals("LADA", driver.findElement(By.id("brandShow")).getText());
        assertEquals("Kalina", driver.findElement(By.id("modelShow")).getText());
        assertEquals("359000", driver.findElement(By.id("priceShow")).getText());
        assertEquals("166000", driver.findElement(By.id("mileageShow")).getText());
        element = driver.findElement(By.className("btn-danger"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        exampls = driver.findElements(By.className("orderShow"));
        assertEquals(allOrders.size(), exampls.size());
    }
}
