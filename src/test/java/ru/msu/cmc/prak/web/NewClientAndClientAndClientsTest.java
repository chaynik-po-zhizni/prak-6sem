package ru.msu.cmc.prak.web;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.entities.Brand;
import ru.msu.cmc.prak.entities.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class NewClientAndClientAndClientsTest extends CommonTest {
    @Test
    void clientsTest1() {
        List<Client> allClients = clientDAO.getAll();
        driver.findElement(By.linkText("Клиенты")).click();
        driver.findElement(By.linkText("Добавить нового клиента")).click();
        String testName = "Test Name";
        String testPhone = "0000000000";
        String testAddr = "Test Address";
        String testEmail = "Test@Email";
        String newTestName = "Test Name new";
        String newTestPhone = "0000000001";

        driver.findElement(By.name("name")).sendKeys(testName);
        driver.findElement(By.name("phone")).sendKeys(testPhone);
        WebElement element = driver.findElement(By.className("btn-success"));
        driver.executeScript("arguments[0].click();", element);
        wait100();

        assertEquals(testName, driver.findElement(By.id("nameInput")).getAttribute("value"));
        assertEquals(testPhone, driver.findElement(By.id("phoneInput")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("mailInput")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("addressInput")).getAttribute("value"));

        driver.findElement(By.name("name")).sendKeys(" new");
        driver.findElement(By.name("phoneString")).clear();
        driver.findElement(By.name("phoneString")).sendKeys(newTestPhone);
        driver.findElement(By.name("address")).sendKeys(testAddr);
        driver.findElement(By.name("email")).sendKeys(testEmail);

        wait100();
        wait100();
        element = driver.findElement(By.id("editButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        wait100();

        assertEquals(newTestName, driver.findElement(By.id("nameInput")).getAttribute("value"));
        assertEquals(newTestPhone, driver.findElement(By.id("phoneInput")).getAttribute("value"));
        assertEquals(testEmail, driver.findElement(By.id("mailInput")).getAttribute("value"));
        assertEquals(testAddr, driver.findElement(By.id("addressInput")).getAttribute("value"));
        driver.findElement(By.linkText("Клиенты")).click();
        List<WebElement> exampls = driver.findElements(By.className("clientShow"));
        assertEquals(allClients.size() + 1, exampls.size());
        driver.findElement(By.name("email")).sendKeys(testEmail);
        driver.findElement(By.id("findClients")).click();
        wait100();
        exampls = driver.findElements(By.className("clientShow"));
        boolean flag = false;
        String t = null;
        for (WebElement cell : exampls) {
            t = cell.findElement(By.id("phoneShow")).getText();
            if (t != null && t.equals(newTestPhone)) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
        element = driver.findElement(By.id("findClients"));
        driver.executeScript("arguments[0].click();", element);
        exampls = driver.findElements(By.className("clientShow"));
        assertEquals(allClients.size() + 1, exampls.size());

        driver.findElement(By.name("name")).sendKeys("Test");
        driver.findElement(By.id("findClients")).click();
        wait100();
        exampls = driver.findElements(By.className("clientShow"));
        flag = false;
        for (WebElement cell : exampls) {
            t = cell.findElement(By.id("phoneShow")).getText();
            if (t != null && t.equals(newTestPhone)) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
        element = driver.findElement(By.id("findClients"));
        driver.executeScript("arguments[0].click();", element);
        exampls = driver.findElements(By.className("clientShow"));
        assertEquals(allClients.size() + 1, exampls.size());


        driver.findElement(By.name("phone")).sendKeys(newTestPhone);
        element = driver.findElement(By.id("findClients"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        exampls = driver.findElements(By.className("clientShow"));
        assertEquals(1, exampls.size());
        assertEquals(newTestName, exampls.get(0).findElement(By.id("nameShow")).getText());
        assertEquals(newTestPhone, exampls.get(0).findElement(By.id("phoneShow")).getText());
        assertEquals(testEmail, exampls.get(0).findElement(By.id("mailShow")).getText());
        assertEquals(testAddr, exampls.get(0).findElement(By.id("addressShow")).getText());
        element = driver.findElement(By.id("infoButton"));
        driver.executeScript("arguments[0].click();", element);
        wait100();

        element =  driver.findElement(By.className("btn-danger"));
        driver.executeScript("arguments[0].click();", element);
        wait100();
        exampls = driver.findElements(By.className("clientShow"));
        assertEquals(allClients.size(), exampls.size());
    }

    @Test
    void clientsTest2() {
        driver.findElement(By.linkText("Клиенты")).click();
        driver.findElement(By.name("phone")).sendKeys("9270868596");
        driver.findElement(By.id("findClients")).click();
        wait100();
        driver.findElement(By.id("infoButton")).click();
        wait100();
        driver.findElement(By.className("btn-info")).click();
        wait100();

        assertEquals(rootUrl + "orders?phone=9270868596",
                driver.getCurrentUrl());
    }

    @Test
    void clientsTestError() {
        driver.findElement(By.linkText("Клиенты")).click();
        driver.findElement(By.name("phone")).sendKeys("9270868596");
        driver.findElement(By.id("findClients")).click();
        wait100();
        driver.findElement(By.id("infoButton")).click();
        wait100();
        driver.findElement(By.name("phoneString")).clear();
        driver.findElement(By.name("phoneString")).sendKeys("9181040972");
        driver.findElement(By.id("editButton")).click();
        wait100();
        assertEquals(rootUrl + "client/edit",
                driver.getCurrentUrl());
    }
}
