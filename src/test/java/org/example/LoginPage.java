package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public WebDriver driver;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = ".signup__btn-inner")
    private WebElement signWithGoogle;

    @FindBy(css = ".signup__form .signup__submit")
    private WebElement signUpButton;

    @FindBy(id = "emailError")
    private WebElement emailError;

    @FindBy(id = "loginError")
    private WebElement loginError;

    @FindBy(id = "passwordError")
    private WebElement passwordError;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickSignUp() {
        signUpButton.click();
    }

    public boolean enableSignUp() {
        return signUpButton.isEnabled();
    }

    public boolean signWithGoogle() {
        return signWithGoogle.isDisplayed();
    }

    public String getEmailError() {
        return emailError.getText();
    }

    public String getPasswordError() {
        return passwordError.getText();
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public String loginError() {
        return loginError.getText();
    }

    public String getEmailAttribute(String attributeName) {
        return emailField.getAttribute(attributeName);
    }

    public String getPasswordAttribute(String attributeName) {
        return passwordField.getAttribute(attributeName);
    }

}
