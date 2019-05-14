import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.io.File;
import java.util.concurrent.TimeUnit;
import static java.lang.Math.random;

public class Zadanie12 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }


    @Test

    public void AddNewProduct() {

    double uniqueIndexDoub = random();
    int uniqueIndex = (int)(uniqueIndexDoub*100000);

    driver.get("http://localhost/litecart/admin/");

    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();

    driver.findElement(By.linkText("Catalog")).click();
    driver.findElement(By.linkText("Add New Product")).click();

    //General
    driver.findElement(By.cssSelector("a[href*=tab-general]")).click();
    driver.findElement(By.xpath("//label[contains(text(),'Enabled')]")).click();
    driver.findElement(By.cssSelector("input[name^=name]")).sendKeys("tovar"+uniqueIndex);
    driver.findElement(By.cssSelector("input[name=code]")).sendKeys(""+uniqueIndex);

    List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type=checkbox]"));
    for (int i=0; i < checkboxes.size(); i++){
        if (checkboxes.get(i).isSelected())
           {
           checkboxes.get(i).click();
           }
    }
    List<WebElement> categories = driver.findElements(By.cssSelector("input[name^=categories]"));
    categories.get(categories.size()-1).click();
    List<WebElement> product_groups = driver.findElements(By.cssSelector("input[name^=product_groups]"));
    product_groups.get(product_groups.size()-1).click();
    driver.findElement(By.cssSelector("input[name=quantity]")).clear();
    driver.findElement(By.cssSelector("input[name=quantity]")).sendKeys(""+uniqueIndex);
    Select selectCategory = new Select(driver.findElement(By.cssSelector("select[name=default_category_id]")));
    selectCategory.selectByValue("2");
    Select selectQuantity = new Select(driver.findElement(By.cssSelector("select[name=quantity_unit_id]")));
    selectQuantity.selectByValue("1");
    Select selectSize = new Select(driver.findElement(By.cssSelector("select[name=delivery_status_id]")));
    selectSize.selectByValue("1");
    Select selectSold = new Select(driver.findElement(By.cssSelector("select[name=sold_out_status_id]")));
    selectSold.selectByValue("2");
    driver.findElement(By.cssSelector("input[name=date_valid_from")).sendKeys("14-05-2019");
    driver.findElement(By.cssSelector("input[name=date_valid_to")).sendKeys("14-05-2020");
    File file = new File(new File("src/test/java/utka.png").getAbsolutePath());
    driver.findElement(By.cssSelector("input[name^=new_images]")).sendKeys(""+file);
    //Information
    driver.findElement(By.linkText("Information")).click();
    Select selectManufacturer = new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]")));
    selectManufacturer.selectByValue("1");
    driver.findElement(By.cssSelector("input[name=keywords")).sendKeys(""+uniqueIndex);
    driver.findElement(By.cssSelector("input[name^=short_description")).sendKeys("Short Description "+uniqueIndex);
    driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Description "+uniqueIndex);
    driver.findElement(By.cssSelector("input[name^=head_title")).sendKeys("Head Title "+uniqueIndex);
    driver.findElement(By.cssSelector("input[name^=meta_description")).sendKeys("Meta Description "+uniqueIndex);
    //Prices
    driver.findElement(By.linkText("Prices")).click();
    driver.findElement(By.cssSelector("input[name=purchase_price]")).clear();
    driver.findElement(By.cssSelector("input[name=purchase_price]")).sendKeys(""+uniqueIndex);
    Select selectPrice = new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
    selectPrice.selectByValue("USD");
    List<WebElement> prices = driver.findElements(By.cssSelector("input[name^=prices]"));
    prices.get(0).clear();
    prices.get(0).sendKeys("100");
    prices.get(1).clear();
    prices.get(1).sendKeys("150");
    //Save
    driver.findElement(By.cssSelector("button[name=save]")).click();
    //New Product is presented
    Assert.assertTrue("New product not presented",driver.findElement(By.linkText("tovar"+uniqueIndex)).isEnabled());
    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }

}


