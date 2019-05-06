package sel.b41;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Zadanie8 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void stickers() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> list1 = driver.findElements(By.xpath("//ul[@class='listing-wrapper products']/li"));
        for(int i=0; i < list1.size(); i++) {
            list1 = driver.findElements(By.xpath("//ul[@class='listing-wrapper products']/li"));
            List<WebElement> a = list1.get(i).findElements(By.xpath(".//*[contains(@class, 'sticker')]"));
            Assert.assertEquals(a.size(), 1);
        }
    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }
}