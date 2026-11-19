package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends  BasePage{

    private  final By usernameField =
            By.xpath("//*[@data-test='username-field']");

    private final By passwordField =
            By.xpath("//*[@data-test='password-field']");

    private final By loginButton =
            By.xpath("//*[@data-test='login-button']");

    private final By loginError =
            By.xpath("//div[contains(@class, 'header__text__error')]");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public void enterLogin(String username){
        type(usernameField, username);

        wait.until(
                ExpectedConditions.attributeToBe(usernameField, "value", username)
        );
    }

    public void enterPassword(String password){
        type(passwordField, password);
    }

    public void clickLogin(){
        click(loginButton);
    }

    public void login(String username, String password){
        enterLogin(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isLoginErrorDisplayed(){
        try {
            return getText(loginError).equals("Incorrect username or password.");

        } catch (TimeoutException e) {
            return false;
        }
    }
}
