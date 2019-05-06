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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Zadanie9 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void sort() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //------------------------------------------------------------------------
        // 1-a
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<WebElement> ZOnTopNames = driver.findElements(By.xpath("//tr[@class='row']//td[5]"));
        List<String> ZOnTopList = new ArrayList<String>();
        for (WebElement zoneontop : ZOnTopNames) {
            String ZOnTopText = zoneontop.getText();
            ZOnTopList.add(ZOnTopText);
        }
        List<String> sortedZOnTopList = ZOnTopList;
        sortedZOnTopList.sort(Comparator.naturalOrder());
        Assert.assertEquals("not equal", ZOnTopList, sortedZOnTopList);
        //------------------------------------------------------------------------
        // 1-b
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        int a = driver.findElements(By.xpath("//tr[@class='row']")).size();
        for (int i = 0; i < a; i++) {
            String zonesCount = driver.findElements(By.xpath("//tr[@class='row']")).get(i).findElement(By.xpath("./td[6]")).getText();
            if (!zonesCount.equals("0")) {
                driver.findElements(By.xpath("//tr[@class='row']")).get(i).findElement(By.xpath(".//i[@class='fa fa-pencil']")).click();
                List<WebElement> ZNames = driver.findElements(By.xpath(".//table[@class='dataTable']//td[3]"));
                List<String> ZList = new ArrayList<String>();
                for (WebElement zone : ZNames) {
                    String ZText = zone.getText();
                    ZList.add(ZText);
                }
                ZList.remove(ZNames.size() - 1);
                List<String> sortedZList = ZList;
                sortedZList.sort(Comparator.naturalOrder());
                Assert.assertEquals("not equal", ZList, sortedZList);
                driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }
        //------------------------------------------------------------------------
        // 2
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> Countries2 = driver.findElements(By.cssSelector("table.dataTable tr.row"));
        int countriesCount2 = Countries2.size();
        for (int k = 0; k < countriesCount2; k++) {
            List<WebElement> countryLinks2 = driver.findElements(By.cssSelector("table.dataTable tr.row"));
            WebElement link = countryLinks2.get(k).findElement(By.cssSelector("td:nth-child(3) a"));
            link.click();
            List<WebElement> selectZones2 = driver.findElements(By.cssSelector("#table-zones tr td:nth-child(3) select"));
            List<String> selectZonesList2 = new ArrayList<String>();
            for (WebElement oneselectZones2 : selectZones2) {
                String selectZonesText = oneselectZones2.getText();
                selectZonesList2.add(selectZonesText);
                }
            List<String> sortedselectZonesList2 = selectZonesList2;
            sortedselectZonesList2.sort(Comparator.naturalOrder());
            Assert.assertEquals("not equal", selectZonesList2, sortedselectZonesList2);
            driver.navigate().back();
        }
    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }

}
