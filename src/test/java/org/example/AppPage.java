package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AppPage {
    public WebDriver driver;

    @FindBy(css = ".user-profile .user-profile__button")
    private WebElement profileButton;

    @FindBy(css = ".user-profile .user-profile__buttons .user-profile__button-item:nth-of-type(5)")
    private WebElement logoutButton;

    public AppPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public boolean isLoggedIn() {
        return profileButton.isDisplayed();
    }

    public void logout() {
        profileButton.click();
        logoutButton.click();
    }
}
