import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class MainTests {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://intershop5.skillbox.ru");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomepageBlocks() {
        var promotionsHeader = driver.findElement(By.xpath("//h2[text()='Распродажа']"));
        assertTrue("Блок 'Распродажа' не найден.", promotionsHeader.isDisplayed());
        var newArrivalsHeader = driver.findElement(By.xpath("//h2[text()='Новые поступления']"));
        assertTrue("Блок 'Новые поступления' не найден.", newArrivalsHeader.isDisplayed());
    }

    @Test
    public void testSearchProduct() {
        driver.findElement(By.name("s")).sendKeys("iPad Air 2020 64gb wi-fi");
        driver.findElement(By.cssSelector(".searchsubmit")).click();
        var expectedUrl = "https://intershop5.skillbox.ru/product/ipad-air-2020-64gb-wi-fi/";
        assertEquals("URL не соответствует ожидаемому.", expectedUrl, driver.getCurrentUrl());
        var productTitle = driver.findElement(By.cssSelector(".product_title")).getText();
        assertEquals("Название товара не соответствует искомому.", "iPad Air 2020 64gb wi-fi", productTitle);
    }

    @Test
    public void testNavigateToBooksCategory() {
        driver.findElement(By.xpath("//h4[text()='Книги']")).click();
        var actualTitle = driver.findElement(By.cssSelector(".entry-title")).getText();
        assertEquals("Заголовок страницы не соответствует ожидаемому.", "КНИГИ", actualTitle);
    }

    @Test
    public void testNavigateToElectronicsPadCategory() {
        driver.findElement(By.xpath("//h4[text()='Планшеты']")).click();
        var actualTitle = driver.findElement(By.cssSelector(".entry-title")).getText();
        assertEquals("Заголовок страницы не соответствует ожидаемому.", "ПЛАНШЕТЫ", actualTitle);
    }

    @Test
    public void testNavigateToElectronicsPhotoVideoCategory() {
        driver.findElement(By.xpath("//h4[text()='Фотоаппараты']")).click();
        var actualTitle = driver.findElement(By.cssSelector(".entry-title")).getText();
        assertEquals("Заголовок страницы не соответствует ожидаемому.", "ФОТО/ВИДЕО", actualTitle);
    }
}