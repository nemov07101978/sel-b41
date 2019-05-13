package sel.b41;

import com.sun.jmx.snmp.Timestamp;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Float.parseFloat;
import static java.lang.Math.random;


public class Zadanie11 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }



   @Test

    public void UserRegistration() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long Index = timestamp.getSysUpTime();

        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.xpath("//a[contains(text(),'New customers click here')]")).click();
        driver.findElement(By.cssSelector("input[name='tax_id']")).sendKeys("ID_"+Index+"" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='company']")).sendKeys("AngolaINC" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys("TesterFirstName" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys("TesterLastName" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='address1']")).sendKeys("TesterAddress1" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='address2']")).sendKeys("TesterAddress2" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='postcode']")).sendKeys("12345" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys("TesterCity" + Keys.ENTER);
        Select selectCountry = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        selectCountry.selectByValue("US");
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].selectedIdex = 5; arguments[0].dispatchEvent(new Event('change'));",
        driver.findElement(By.cssSelector("select[name=zone_code]")));
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("tester"+Index+"@gmail.com" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys(Keys.HOME + "+7"+Index+"" + Keys.ENTER);

        if (driver.findElement(By.cssSelector("input[name='newsletter']")).getAttribute("value").equals("0")){
           driver.findElement(By.cssSelector("input[name='newsletter']")).click();
        }

        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("pass"+Index+"" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys("pass"+Index+"");
        driver.findElement(By.cssSelector("button[name='create_account']")).click();
        wait.until(ExpectedConditions.urlContains("http://localhost/litecart/en"));
        Assert.assertEquals("Registration FAIL", driver.findElement(By.cssSelector("div.notice.success")).getText(), "Your customer account has been created.");

        //LogOut
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#slider-wrapper")));
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
        //LogIn
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name=email]")));

        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("tester"+Index+"@gmail.com");//Email Address
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("pass"+Index+""+ Keys.ENTER);//Password
    }


    @After
    public void stop () {
        driver.quit();
        driver = null;
    }
}
