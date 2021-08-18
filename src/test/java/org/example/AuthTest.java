package org.example;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class AuthTest {
    public static LoginPage loginPage;
    public static AppPage appPage;
    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        appPage = new AppPage(driver);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("loginpage"));
    }

    @Test
    //открытие страницы авторизации
    public void openPageTest() {
        loginPage.clearFields();

        Assert.assertEquals(ConfProperties.getProperty("placeholderEmail"), loginPage.getEmailAttribute("placeholder"));
        Assert.assertEquals(ConfProperties.getProperty("placeholderPassword"), loginPage.getPasswordAttribute("placeholder"));
        Assert.assertTrue(loginPage.enableSignUp());
        Assert.assertTrue(loginPage.signWithGoogle());
    }

    @Test
    //авторизация с пустыми полями email и password
    public void emptyAuthorizationTest() {
        loginPage.clearFields();
        loginPage.clickSignUp();

        Assert.assertEquals(ConfProperties.getProperty("emptyEmailError"), loginPage.getEmailError());
        Assert.assertEquals(ConfProperties.getProperty("passwordError"), loginPage.getPasswordError());
    }

    @Test
    //заполнение только поля email
    public void wrongPasswordTest() {
        loginPage.clearFields();
        loginPage.enterEmail("test@q.com");
        loginPage.clickSignUp();

        Assert.assertEquals(loginPage.getEmailAttribute("value"), "test@q.com");
        Assert.assertEquals(ConfProperties.getProperty("passwordError"), loginPage.getPasswordError());
        Assert.assertEquals(loginPage.getEmailAttribute("aria-invalid"), "false");
    }

        @Test
        //некорректное заполнение поля email
        public void incorrectEmailTest() {
            loginPage.clearFields();
            loginPage.enterEmail("test");
            loginPage.clickSignUp();

            Assert.assertEquals(ConfProperties.getProperty("incorrectEmailError"), loginPage.getEmailError());
        }

        @Test
        //заполнение только поля password
        public void wrongEmailTest() {
            loginPage.clearFields();
            loginPage.enterPassword("test");
            loginPage.clickSignUp();

            Assert.assertEquals(ConfProperties.getProperty("emptyEmailError"), loginPage.getEmailError());
            Assert.assertEquals(loginPage.getPasswordAttribute("aria-invalid"), "false");
        }

        @Test
        //ввод неверных значений email и password
        public void wrongAuthorizationTest() {
            loginPage.clearFields();
            loginPage.enterEmail("test@q.com");
            loginPage.enterPassword("test");
            loginPage.clickSignUp();

            Assert.assertEquals(loginPage.getPasswordAttribute("aria-invalid"), "true");
            Assert.assertEquals(loginPage.getEmailAttribute("aria-invalid"), "false");
            Assert.assertEquals(ConfProperties.getProperty("loginError"), loginPage.loginError());
        }

        @Test
        //ввод корректных значений email и password
        public void rightAuthorizationTest() {
            loginPage.clearFields();
            loginPage.enterEmail("nasty_nega@mail.ru");
            loginPage.enterPassword("Qwerty123");
            loginPage.clickSignUp();

            Assert.assertTrue(appPage.isLoggedIn());
            Assert.assertEquals(driver.getCurrentUrl(), "https://miro.com/app/dashboard/");

            appPage.logout();
        }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
