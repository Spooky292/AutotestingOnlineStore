import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class AuthorizationTests {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://intershop5.skillbox.ru");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static final String USERNAME = "tester@tester";
    private static final String PASSWORD = "123";
    private static final String WELCOME_MESSAGE = "Привет tester@tester (Выйти)";
    private static final String INCORRECT_USERNAME = "tester@tester";
    private static final String INCORRECT_PASSWORD = "1231";
    private static final String ERROR_MESSAGE = "Веденный пароль для пользователя tester@tester неверный. Забыли пароль?";

    @Test
    public void testLogin() {
        driver.findElement(By.cssSelector(".login-woocommerce")).click();
        driver.findElement(By.id("username")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector(".show-password-input")).click();
        driver.findElement(By.id("rememberme")).click();
        driver.findElement(By.name("login")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='woocommerce-MyAccount-content']/p"))).getText();
        assertEquals("Приветствие не отображается корректно!", WELCOME_MESSAGE, actualMessage);
    }

    @Test
    public void testLoginFail() {
        driver.findElement(By.cssSelector(".login-woocommerce")).click();
        driver.findElement(By.id("username")).sendKeys(INCORRECT_USERNAME);
        driver.findElement(By.id("password")).sendKeys(INCORRECT_PASSWORD);
        driver.findElement(By.cssSelector(".show-password-input")).click();
        driver.findElement(By.id("rememberme")).click();
        driver.findElement(By.name("login")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var actualErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'woocommerce-error')]"))).getText();
        assertEquals("Сообщение об ошибке не соответствует ожидаемому.", ERROR_MESSAGE, actualErrorMessage);
    }
}