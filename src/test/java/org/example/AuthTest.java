package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class AuthTest {
    public static LoginPage loginPage;
    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("loginpage"));

        loginPage = new LoginPage(driver);
    }

    @Test
    //открытие страницы логина
    public void openPageTest() {
        Assert.assertEquals(ConfProperties.getProperty("placeholderEmail"), loginPage.getEmailAttribute("placeholder"));
        Assert.assertEquals(ConfProperties.getProperty("placeholderPassword"), loginPage.getPasswordAttribute("placeholder"));
        Assert.assertTrue(loginPage.enableSignUp());
        Assert.assertTrue(loginPage.signWithGoogle());
    }

    @Test
    //пустые логин и пароль
    public void emptyAuthorizationTest() {
        loginPage.clickSignUp();
        Assert.assertEquals(ConfProperties.getProperty("emailError"), loginPage.getEmailError());
        Assert.assertEquals(ConfProperties.getProperty("passwordError"), loginPage.getPasswordError());
    }

    @Test
    //ввод неверных значений
    public void wrongAuthorizationTest() {
        loginPage.enterEmail("test@q.com");
        loginPage.enterPassword("test");
        loginPage.clickSignUp();
        Assert.assertEquals(loginPage.getPasswordAttribute("aria-invalid"), "true");
        Assert.assertEquals(loginPage.getEmailAttribute("aria-invalid"), "false");
        Assert.assertEquals(ConfProperties.getProperty("loginError"), loginPage.loginError());
    }

    @Test
    //ввод корректных значений
    public void rightAuthorizationTest() {
        loginPage.enterEmail("nasty_nega@mail.ru");
        loginPage.enterPassword("Qwerty123");
        loginPage.clickSignUp();
        WebElement profileElement = driver.findElement(By.cssSelector(".user-profile .user-profile__button"));
        Assert.assertTrue(profileElement.isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), "https://miro.com/app/dashboard/");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
