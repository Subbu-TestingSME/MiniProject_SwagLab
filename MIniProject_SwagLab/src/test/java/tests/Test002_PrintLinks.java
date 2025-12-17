package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.util.List;

public class Test002_PrintLinks {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void printAllLinks() {
        List<WebElement> links = driver.findElements(By.tagName("link"));
        for (WebElement link : links) {
            System.out.println(link.getAttribute("href"));
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
