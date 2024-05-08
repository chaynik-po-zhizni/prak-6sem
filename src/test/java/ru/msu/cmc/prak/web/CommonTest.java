package ru.msu.cmc.prak.web;

import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.prak.DAO.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = "classpath:application.properties")
public class CommonTest {
    @Autowired
    protected BrandDAO brandDAO;
    @Autowired
    protected CarDAO carDAO;
    @Autowired
    protected ClientDAO clientDAO;
    @Autowired
    protected ModelDAO modelDAO;
    @Autowired
    protected OrderDAO orderDAO;
    @Autowired
    protected SessionFactory sessionFactory;
    @Autowired
    protected ChromeDriver driver;


    @Value("${websiteUrl}")
    protected String rootUrl;

    protected void wait100() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
    }

}
