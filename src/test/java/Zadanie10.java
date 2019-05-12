package sel.b41;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Float.parseFloat;


public class Zadanie10 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    //а
    public void Litecart_TextProductEqual() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> Products = driver.findElements(By.cssSelector(".product"));
        int productsCount = Products.size();
        for (int i = 0; i < productsCount; i++) {
            List<WebElement> resultLinks = driver.findElements(By.cssSelector(".product"));
            String nameOnMainPage = resultLinks.get(i).findElement(By.cssSelector("div.name")).getText();
            resultLinks.get(i).click();
            String nameOnProductPage = driver.findElement(By.cssSelector("h1.title")).getText();
            Assert.assertEquals("not equal", nameOnMainPage, nameOnProductPage);
            driver.navigate().back();
        }
    }

    @Test
    //б
    public void Litecart_PriceEqual() {
        driver.get("http://localhost/litecart/en/");
        String prices;
        String pricesOnPage;
        String countOfPrices;
        List<WebElement> Products = driver.findElements(By.cssSelector(".product"));
        int productsCount = Products.size();
        for (int i = 0; i < productsCount; i++) {
            List<WebElement> resultLinks = driver.findElements(By.cssSelector(".product"));
            countOfPrices = resultLinks.get(i).findElement(By.cssSelector("div.price-wrapper")).getAttribute("childElementCount");
            if (countOfPrices.equals("2")){
                prices = resultLinks.get(i).findElement(By.cssSelector("s.regular-price")).getText()+" "+resultLinks.get(i).findElement(By.cssSelector("strong.campaign-price")).getText();
            }
            else prices = resultLinks.get(i).findElement(By.cssSelector("span.price")).getText();
            resultLinks.get(i).click();

            WebElement information = driver.findElement(By.cssSelector("div.information div.price-wrapper"));
            countOfPrices = information.getAttribute("childElementCount");
            if (countOfPrices.equals("2")){
                pricesOnPage = information.findElement(By.cssSelector("s.regular-price")).getText()+" "+information.findElement(By.cssSelector("strong.campaign-price")).getText();
            }
            else pricesOnPage = information.findElement(By.cssSelector("span.price")).getText();

            Assert.assertEquals("not equal", pricesOnPage, prices);
            driver.navigate().back();
        }
    }

    //в
    @Test
    public void Litecart_RegularPrice() {
        driver.get("http://localhost/litecart/en/");
        int R,G,B;
        String countOfPrices;
        String rgb;
        List<WebElement> Products = driver.findElements(By.cssSelector(".product"));
        int productsCount = Products.size();
        for (int i = 0; i < productsCount; i++) {
            List<WebElement> resultLinks = driver.findElements(By.cssSelector(".product"));
            countOfPrices = resultLinks.get(i).findElement(By.cssSelector("div.price-wrapper")).getAttribute("childElementCount");
            if (countOfPrices.equals("2")) {
                //tag 's' for .regular-price element
                Assert.assertTrue("not line-through", resultLinks.get(i).findElement(By.cssSelector("s.regular-price")).isEnabled());
                //RGB
                rgb = resultLinks.get(i).findElement(By.cssSelector(".regular-price")).getCssValue("color");
                R = Color.fromString(rgb).getColor().getRed();
                G = Color.fromString(rgb).getColor().getGreen();
                B = Color.fromString(rgb).getColor().getBlue();
                Assert.assertTrue("not GREY color", (R == G) && (R == B) );

                resultLinks.get(i).click();

                WebElement information = driver.findElement(By.cssSelector("div.information div.price-wrapper"));
                //tag 's' for .regular-price element
                Assert.assertTrue("not line-through on product page", information.findElement(By.cssSelector("s.regular-price")).isEnabled());
                rgb = information.findElement(By.cssSelector(".regular-price")).getCssValue("color");
                R = Color.fromString(rgb).getColor().getRed();
                G = Color.fromString(rgb).getColor().getGreen();
                B = Color.fromString(rgb).getColor().getBlue();
                Assert.assertTrue("not GREY color on product page", (R == G) && (R == B) );

                driver.navigate().back();
            }
        }
    }

    //г
    @Test
    public void Litecart_AkcionPriceRed() {
        driver.get("http://localhost/litecart/en/");
        int R,G,B;
        String countOfPrices;
        String rgb;
        List<WebElement> Products = driver.findElements(By.cssSelector(".product"));
        int productsCount = Products.size();
        for (int i = 0; i < productsCount; i++) {
            List<WebElement> resultLinks = driver.findElements(By.cssSelector(".product"));
            countOfPrices = resultLinks.get(i).findElement(By.cssSelector("div.price-wrapper")).getAttribute("childElementCount");
            if (countOfPrices.equals("2")) {
                //tag 'strong' for .campaign-price element
                Assert.assertTrue("not bold text)", resultLinks.get(i).findElement(By.cssSelector("strong.campaign-price")).isEnabled());
                //RGB
                rgb = resultLinks.get(i).findElement(By.cssSelector(".campaign-price")).getCssValue("color");
                R = Color.fromString(rgb).getColor().getRed();
                G = Color.fromString(rgb).getColor().getGreen();
                B = Color.fromString(rgb).getColor().getBlue();
                Assert.assertTrue("not RED", (R > 0) && (G == 0) && (B == 0) );

                resultLinks.get(i).click();

                WebElement information = driver.findElement(By.cssSelector("div.information div.price-wrapper"));
                //tag 'strong' for .campaign-price element
                Assert.assertTrue("not bold text on product page", information.findElement(By.cssSelector("strong.campaign-price")).isEnabled());
                rgb = information.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
                R = Color.fromString(rgb).getColor().getRed();
                G = Color.fromString(rgb).getColor().getGreen();
                B = Color.fromString(rgb).getColor().getBlue();
                Assert.assertTrue("not RED", (R > 0) && (G == 0) && (B == 0) );

                driver.navigate().back();
            }
        }
    }

    //д
    @Test
    public void Litecart_AkcionPrice() {
        driver.get("http://localhost/litecart/en/");
        String countOfPrices;
        String sizeRegular,sizeCampaign;
        float sizeRegularFl,sizeCampaignFl;
        List<WebElement> Products = driver.findElements(By.cssSelector(".product"));
        int productsCount = Products.size();
        for (int i = 0; i < productsCount; i++) {
            List<WebElement> resultLinks = driver.findElements(By.cssSelector(".product"));
            countOfPrices = resultLinks.get(i).findElement(By.cssSelector("div.price-wrapper")).getAttribute("childElementCount");
            if (countOfPrices.equals("2")) {

                sizeRegular = resultLinks.get(i).findElement(By.cssSelector(".regular-price")).getCssValue("font-size");
                sizeCampaign = resultLinks.get(i).findElement(By.cssSelector(".campaign-price")).getCssValue("font-size");
                sizeRegularFl = parseFloat(sizeRegular.replaceAll("[a-zA-Z]",""));
                sizeCampaignFl = parseFloat(sizeCampaign.replaceAll("[a-zA-Z]",""));
                Assert.assertTrue("font-size < regular", sizeRegularFl<sizeCampaignFl );

                resultLinks.get(i).click();

                WebElement information = driver.findElement(By.cssSelector("div.information div.price-wrapper"));
                sizeRegular = information.findElement(By.cssSelector(".regular-price")).getCssValue("font-size");
                sizeCampaign = information.findElement(By.cssSelector(".campaign-price")).getCssValue("font-size");
                sizeRegularFl = parseFloat(sizeRegular.replaceAll("[a-zA-Z]",""));
                sizeCampaignFl = parseFloat(sizeCampaign.replaceAll("[a-zA-Z]",""));
                Assert.assertTrue("font-size < regular on product page", sizeRegularFl<sizeCampaignFl );

                driver.navigate().back();
            }
        }
    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }

  }
