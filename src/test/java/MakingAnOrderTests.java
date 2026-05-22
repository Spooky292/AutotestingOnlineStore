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

public class MakingAnOrderTests {
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

    private static final String USERNAME = "tester@tester";
    private static final String PASSWORD = "123";
    private static final String EXPECTED_WELCOME_MESSAGE = "Привет tester@tester (Выйти)";
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
    private static final String BILLING_FIRST_NAME_ID = "billing_first_name";
    private static final String BILLING_LAST_NAME_ID = "billing_last_name";
    private static final String BILLING_ADDRESS_ID = "billing_address_1";
    private static final String BILLING_CITY_ID = "billing_city";
    private static final String BILLING_STATE_ID = "billing_state";
    private static final String BILLING_POSTCODE_ID = "billing_postcode";
    private static final String BILLING_PHONE_ID = "billing_phone";
    private static final String FIRST_NAME = "Иван";
    private static final String LAST_NAME = "Иванов";
    private static final String ADDRESS = "ул.Советников 11";
    private static final String CITY = "Москва";
    private static final String STATE = "Московская область";
    private static final String POSTCODE = "131149";
    private static final String PHONE = "79621590831";
    private static final String PAYMENT_METHOD_COD_ID = "payment_method_cod";
    private static final String PAYMENT_METHOD_BACS_ID = "payment_method_bacs";
    private static final String PLACE_ORDER_BUTTON_ID = "place_order";
    private static final String EXPECTED_ORDER_MESSAGE = "Заказ получен";

    @Test
    public void testAddToCart() {
        driver.findElement(By.cssSelector(".login-woocommerce")).click();
        driver.findElement(By.id("username")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector(".show-password-input")).click();
        driver.findElement(By.id("rememberme")).click();
        driver.findElement(By.name("login")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='woocommerce-MyAccount-content']/p"))).getText();
        assertEquals("Приветствие не отображается корректно!", EXPECTED_WELCOME_MESSAGE, actualMessage);
        var catalogLink = driver.findElement(By.id("menu-item-46"));
        catalogLink.click();
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
        driver.findElement(By.id(BILLING_FIRST_NAME_ID)).sendKeys(FIRST_NAME);
        driver.findElement(By.id(BILLING_LAST_NAME_ID)).sendKeys(LAST_NAME);
        driver.findElement(By.id(BILLING_ADDRESS_ID)).sendKeys(ADDRESS);
        driver.findElement(By.id(BILLING_CITY_ID)).sendKeys(CITY);
        driver.findElement(By.id(BILLING_STATE_ID)).sendKeys(STATE);
        driver.findElement(By.id(BILLING_POSTCODE_ID)).sendKeys(POSTCODE);
        driver.findElement(By.id(BILLING_PHONE_ID)).sendKeys(PHONE);
        driver.findElement(By.id(PAYMENT_METHOD_COD_ID)).click();
        driver.findElement(By.id(PAYMENT_METHOD_BACS_ID)).click();
        driver.findElement(By.id(PLACE_ORDER_BUTTON_ID)).click();
        var messageFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Заказ получен')]"))).getText();
        assertEquals("Сообщение о завершении заказа не соответствует ожидаемому.", EXPECTED_ORDER_MESSAGE, messageFinal);
    }
}