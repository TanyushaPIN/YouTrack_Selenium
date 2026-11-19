package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private  final By usernameField =
            By.cssSelector("[data-test='username-field']");

    private final By passwordField =
            By.cssSelector("[data-test='password-field']");

    private final By loginButton =
            By.cssSelector("[data-test='login-button']");

    private final By loginError =
            By.cssSelector("div.header__text__error");

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void  enterLogin(String username){
        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(usernameField)
        );
        field.clear();
        field.sendKeys(username);

        wait.until(
                ExpectedConditions.attributeToBe(usernameField, "value", username)
        );
    }

    public void enterPassword(String password){
        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(passwordField)
        );
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin(){
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        );
        button.click();
    }

    public void login(String username, String password){
        enterLogin(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isLoginErrorDisplayed(){
        try {
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(loginError)
            );
            return error.getText().equals("Incorrect username or password.");

        } catch (TimeoutException e) {
            return false;
        }
    }
}
