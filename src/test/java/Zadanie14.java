import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Zadanie14 {

    private WebDriver driver;
    private WebDriverWait wait;


    private static ExpectedCondition<String> newWindowHandleIs(
            final Set<String> existingWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(existingWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    @Before
    public void start() {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 20);
        }

    @Test
    public void testWindows(){
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        //Countries
        driver.findElement(By.cssSelector("i.fa.fa-flag.fa-stack-1x.icon")).click();
        driver.findElement(By.cssSelector("a[title=Edit]")).click();

        List<WebElement> externalLinks = driver.findElements(By.cssSelector("i.fa.fa-external-link"));
        int linksCount = externalLinks.size();
        for (int i = 0; i < linksCount; i++) {
            System.out.println("iteration = "+(i+1));
            List<WebElement> Links = driver.findElements(By.cssSelector("i.fa.fa-external-link"));
            WebElement linkExternal = Links.get(i);

            String mainWindow = driver.getWindowHandle();// запоминаем идентификатор текущего окна
            Set<String> allWindows = driver.getWindowHandles(); // запоминаем идентификаторы уже открытых окон
            linkExternal.click(); // открывает новое окно
            String newWindow = wait.until(newWindowHandleIs(allWindows)); // ждем появления нового окна с новым идентификатором
            driver.switchTo().window(newWindow); // переключаемся на новое окно
            driver.close(); // закрываем новое окно
            driver.switchTo().window(mainWindow); // возвращаемся в главное окно
        }
    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }
}


