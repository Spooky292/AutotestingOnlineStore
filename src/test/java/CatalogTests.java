import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CatalogTests {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://intershop5.skillbox.ru/product-category/catalog/");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static final String CATALOG_PAGE_TITLE = "КАТАЛОГ";
    private static final String SEARCH_TERM = "Холодильник";
    private static final String SEARCH_RESULTS_TITLE = "РЕЗУЛЬТАТЫ ПОИСКА: “ХОЛОДИЛЬНИК”";
    private static final String CATEGORY_TITLE = "КАТЕГОРИИ ТОВАРОВ";
    private static final String URL_WASHING_MACHINES = "https://intershop5.skillbox.ru/product-category/catalog/appliances/wash/";
    private static final String TELEVISION_TITLE = "ТЕЛЕВИЗОРЫ";
    private static final String PAGE_TWO_NUMBER = "2";
    private static final String URL_PAGE_EIGHTEEN = "https://intershop5.skillbox.ru/product-category/catalog/page/18/";

    @Test
    public void testNavigateToCatalog() {
        var catalogLink = driver.findElement(By.id("menu-item-46"));
        catalogLink.click();
        var actualTitle = driver.findElement(By.cssSelector(".entry-title")).getText();
        assertEquals("Заголовок страницы не соответствует ожидаемому.", CATALOG_PAGE_TITLE, actualTitle);
        var productCard = driver.findElement(By.cssSelector(".type-product:first-child a"));
        productCard.click();
    }

    @Test
    public void testSearchForFridge() {
        driver.findElement(By.name("s")).sendKeys(SEARCH_TERM);
        driver.findElement(By.cssSelector(".searchsubmit")).click();
        var actualTitle = driver.findElement(By.cssSelector(".entry-title")).getText();
        assertEquals("Заголовок страницы не соответствует ожидаемому.", SEARCH_RESULTS_TITLE, actualTitle);
        var productCard = driver.findElement(By.cssSelector(".type-product:first-child a"));
        productCard.click();
    }

    @Test
    public void testPaginationAndCategoryHeader() {
        var categoryTitle = driver.findElement(By.xpath("//div[contains(@id, 'woocommerce_product_categories-2')]//span[contains(@class, 'widget-title')]")).getText();
        assertEquals("Заголовок страницы не соответствует ожидаемому.", CATEGORY_TITLE, categoryTitle);
    }

    @Test
    public void testNavigateToWashingMachines() {
        driver.findElement(By.cssSelector(".cat-item-22 a")).click();
        var actualUrl = driver.getCurrentUrl();
        assertEquals("URL страниц не соответствует ожидаемому.", URL_WASHING_MACHINES, actualUrl);
        var firstProductLink = driver.findElement(By.cssSelector(".type-product:first-child a"));
        firstProductLink.click();
    }

    @Test
    public void testNavigateToTelevisions() {
        var televisionsLink = driver.findElement(By.cssSelector(".cat-item-25 a"));
        televisionsLink.click();
        var actualTitle = driver.findElement(By.cssSelector(".entry-title")).getText();
        assertEquals("Заголовок страницы не соответствует ожидаемому.", TELEVISION_TITLE, actualTitle);
        var firstProductLink = driver.findElement(By.cssSelector(".type-product:first-child a"));
        firstProductLink.click();
    }

    @Test
    public void testPaginationTwo() {
        var pageTwoLink = driver.findElement(By.cssSelector(".page-numbers[href*='page/2/']"));
        pageTwoLink.click();
        var currentPageTitle = driver.findElement(By.cssSelector(".page-numbers.current")).getText();
        assertEquals("Не удалось перейти на страницу 2.", PAGE_TWO_NUMBER, currentPageTitle);
    }

    @Test
    public void testPaginationEighteen() {
        driver.findElement(By.cssSelector(".page-numbers[href*='page/18/']")).click();
        var actualUrl = driver.getCurrentUrl();
        assertEquals("Не удалось перейти на страницу 18.", URL_PAGE_EIGHTEEN, actualUrl);
    }
}
