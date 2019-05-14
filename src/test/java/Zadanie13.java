import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Zadanie13 {

    private WebDriver driver;
    private WebDriverWait wait;

    public boolean isElementPresent(String locator) {
        try {
            driver.findElement(By.cssSelector(locator));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
     public void litecartWorkWithCart() {

        //Add product to cart - 3 iterations
        for (int i = 0; i < 3; i++) {

            driver.get("http://localhost/litecart/en/");
            driver.findElements(By.cssSelector(".product")).get(0).click();

            int quantity = Integer.parseInt(driver.findElement(By.cssSelector("span.quantity")).getText());
            wait.until(elementToBeClickable(By.cssSelector("button[name=add_cart_product]")));
            //for product with 'Size' select element
            if (isElementPresent("td.options > strong")) {
                Select selectSize = new Select(driver.findElement(By.cssSelector("select[name^=options")));
                selectSize.selectByValue("Small");
            }
            driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
            quantity += 1;
            wait.until(textToBePresentInElement(driver.findElement(By.cssSelector("span.quantity")), "" + quantity + ""));
        }
        //Delete products from cart
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.cssSelector("#cart > a.link")).click();
        int countOfRows = driver.findElements(By.cssSelector("table.dataTable.rounded-corners tr")).size();
        int countOfDucks = driver.findElements(By.cssSelector("li.shortcut")).size();

        for (int i = 0; i < countOfDucks - 1; i++) {
            wait.until(elementToBeClickable(driver.findElements(By.cssSelector("button[name=remove_cart_item]")).get(0)));
            driver.findElements(By.cssSelector("button[name=remove_cart_item]")).get(0).click();
            countOfRows -= 1;
            wait.until(numberOfElementsToBe(By.cssSelector("table.dataTable.rounded-corners tr"), countOfRows));
        }
        driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
        wait.until(invisibilityOf(driver.findElement(By.cssSelector("a.image-wrapper.shadow"))));
        Assert.assertTrue("Not all elements deleted", !isElementPresent("a.image-wrapper.shadow"));
    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }
}
