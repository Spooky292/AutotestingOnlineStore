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

import static org.junit.Assert.*;

public class BasketTests {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://intershop5.skillbox.ru/product-category/catalog/");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static final String URL = "https://intershop5.skillbox.ru/product-category/catalog/electronics/pad/";
    private static final String PRODUCT_SELECTOR = ".cat-item-26 a";
    private static final String FIRST_PRODUCT_SELECTOR = ".type-product:first-child a";
    private static final String ADD_TO_CART_BUTTON_SELECTOR = ".single_add_to_cart_button";
    private static final String SUCCESS_MESSAGE_SELECTOR = ".woocommerce-message";
    private static final String MORE_DETAILS_LINK_SELECTOR = ".wc-forward";
    private static final String COUPON_INPUT = "coupon_code";
    private static final String APPLY_COUPON_BUTTON_NAME = "apply_coupon";
    private static final String COUPON_MESSAGE = "Купон успешно добавлен.";
    private static final String REMOVABLE_COUPON_SELECTOR = "a.woocommerce-remove-coupon[data-coupon='sert500']";
    private static final String COUPON_REMOVAL_MESSAGE = "Купон удален.";
    private static final String INVALID_COUPON_MESSAGE_XPATH = "//ul[contains(@class, 'woocommerce-error')]";
    private static final String INVALID_COUPON_MESSAGE = "Неверный купон.";
    private static final String EXP_INVALID_MESSAGE = "Сообщение об ошибке не корректно!";
    private static final String CHECKOUT_BUTTON_SELECTOR = ".menu-item-31";
    private static final String POST_TITLE_SELECTOR = ".post-title";
    private static final String URL_CHECKOUT = "https://intershop5.skillbox.ru/checkout/";

    @Test
    public void testAddToCart() {
        driver.findElement(By.cssSelector(PRODUCT_SELECTOR)).click();
        assertEquals("URL не соответствует ожидаемому.", URL, driver.getCurrentUrl());
        var firstProductLink = driver.findElement(By.cssSelector(FIRST_PRODUCT_SELECTOR));
        firstProductLink.click();
        var addToCartButton = driver.findElement(By.cssSelector(ADD_TO_CART_BUTTON_SELECTOR));
        addToCartButton.click();
        var successMessage = driver.findElement(By.cssSelector(SUCCESS_MESSAGE_SELECTOR));
        assertTrue("Уведомление о добавлении товара не отображается.", successMessage.isDisplayed());
        var moreDetailsLink = driver.findElement(By.cssSelector(MORE_DETAILS_LINK_SELECTOR));
        moreDetailsLink.click();
        var cartProductName = driver.findElement(By.cssSelector(".product-name"));
        var cartProductNameText = cartProductName.getText();
        assertFalse("Товар не был добавлен в корзину или название пустое.", cartProductNameText.isEmpty());
        var couponInput = driver.findElement(By.id(COUPON_INPUT));
        couponInput.sendKeys("sert500");
        var applyCouponButton = driver.findElement(By.name(APPLY_COUPON_BUTTON_NAME));
        applyCouponButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var additionsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'woocommerce-message')]"))).getText();
        assertEquals("Сообщение о добавлении купона не соответствует ожидаемому.", COUPON_MESSAGE, additionsMessage);
        var dellCouponButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(REMOVABLE_COUPON_SELECTOR)));
        assertNotNull(dellCouponButton);
        dellCouponButton.click();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var dellMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'woocommerce-message')]"))).getText();
        assertEquals("Сообщение о удалении купона не соответствует ожидаемому.", COUPON_REMOVAL_MESSAGE, dellMessage);
        var noCouponInput = driver.findElement(By.id(COUPON_INPUT));
        noCouponInput.sendKeys("NoCupon");
        var applyNoCouponButton = driver.findElement(By.name(APPLY_COUPON_BUTTON_NAME));
        applyNoCouponButton.click();
        var invalidMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_COUPON_MESSAGE_XPATH))).getText();
        assertEquals("Сообщение об ошибке при введении купона не соответствует ожидаемому.", INVALID_COUPON_MESSAGE, invalidMessage);
        assertEquals(EXP_INVALID_MESSAGE, INVALID_COUPON_MESSAGE, invalidMessage);
        var checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CHECKOUT_BUTTON_SELECTOR)));
        checkoutButton.click();
        driver.findElement(By.cssSelector(POST_TITLE_SELECTOR)).click();
        assertEquals("Не удалось перейти на страницу оформления заказа.", URL_CHECKOUT, driver.getCurrentUrl());
    }
}