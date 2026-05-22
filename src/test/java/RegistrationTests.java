import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RegistrationTests {
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

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private String generateRandomEmail(int length) {
        String username = generateRandomString(length - 10);
        return username + "@test.ru";
    }

    private static final String USERNAME = "tester@tester";
    private static final String EMAIL = "tester.tester@ya.by";
    private static final String ERROR_MESSAGE = "Error: Учетная запись с такой почтой уже зарегистировавана. Пожалуйста авторизуйтесь.";

    @Test
    public void testRegistration() {
        driver.findElement(By.cssSelector(".login-woocommerce")).click();
        driver.findElement(By.cssSelector(".custom-register-button")).click();
        var randomUsername = generateRandomString(5);
        driver.findElement(By.id("reg_username")).sendKeys(randomUsername);
        var randomEmail = generateRandomEmail(15);
        driver.findElement(By.id("reg_email")).sendKeys(randomEmail);
        var randomPassword = generateRandomString(8);
        driver.findElement(By.id("reg_password")).sendKeys(randomPassword);
        driver.findElement(By.name("register")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var Message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".content-page"))).getText();
        var expectedMessage = "Регистрация завершена";
        assertEquals("Регистрация не пройдена.", expectedMessage, Message);
    }

    @Test
    public void testRegistrationFail() {
        driver.findElement(By.cssSelector(".login-woocommerce")).click();
        driver.findElement(By.cssSelector(".custom-register-button")).click();
        driver.findElement(By.id("reg_username")).sendKeys(USERNAME);
        driver.findElement(By.id("reg_email")).sendKeys(EMAIL);
        driver.findElement(By.id("reg_password")).sendKeys("123"); // Пароль может быть любым, зависит от логики теста
        driver.findElement(By.name("register")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var actualErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'woocommerce-error')]"))).getText();
        assertEquals("Сообщение об ошибке не соответствует ожидаемому.", ERROR_MESSAGE, actualErrorMessage);
    }
}