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

public class Zadanie17 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
        }

    @Test
    public void litecartProductsLog() {
            driver.get("http://localhost/litecart/admin/");
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin");
            driver.findElement(By.name("login")).click();
            driver.findElement(By.linkText("Catalog")).click();
            driver.findElement(By.linkText("Rubber Ducks")).click();

            List<WebElement> Products = driver.findElements(By.cssSelector("tr.row"));
            int productsCount = Products.size();
            for (int i = 3; i <productsCount; i++) {
                List<WebElement> ProductsSecond = driver.findElements(By.cssSelector("tr.row"));
                ProductsSecond.get(i).findElement(By.cssSelector("td:nth-child(3) a")).click();

                Assert.assertTrue("Log is not empty!",(driver.manage().logs().get("browser").getAll().size() == 0));
                driver.navigate().back();
            }
    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }


}
