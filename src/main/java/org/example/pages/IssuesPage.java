package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IssuesPage extends  BasePage {

    private final By summaryField =
            By.xpath("//textarea[@data-test='summary']");

    private final By descriptionField =
            By.xpath("//div[@data-test='wysiwyg-editor-content']");

    private final By createButton =
            By.xpath("//button[@data-test='submit-button']");

    private final By newIssueButton =
            By.xpath("//*[@data-test='createIssueButton']");

    private final By issuesTab =
            By.xpath("//a[normalize-space()='Issues']");

    private final By issueSummary =
            By.xpath("//h1[@data-test='ticket-summary']");

    private final By searchField =
            By.xpath("//input[@data-test='ring-select__focus']");


    private final String foundIssueId =
            "//a[@data-test='ring-link ticket-id' and normalize-space()='%s']";

    public IssuesPage(WebDriver driver){
        super(driver);
    }

    public void openIssuesTab() {
        click(issuesTab);

        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(newIssueButton)
            );
        } catch (TimeoutException e) {
            System.out.println("После клика URL: " + driver.getCurrentUrl());
            System.out.println("Кнопка создания задачи не появилась");
            throw e;
        }
    }

    public void openCreateIssueForm(){
        click(newIssueButton);
    }
    public void enterSummary(String summary) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(summaryField)
        );

        field.clear();

        for (char character : summary.toCharArray()) {
            field.sendKeys(String.valueOf(character));
        }
    }

    public void enterDescription(String description){
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(descriptionField)
        );
        field.click();
        field.sendKeys(description);
    }

    public void clickCreate(){
        click(createButton);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(issueSummary)
        );

    }

    public boolean isIssueOpened(String summary) {
        try {
            return wait.until(
                    ExpectedConditions.textToBe(issueSummary, summary)
            );
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void searchIssue(String query){
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchField)
        );
        field.click();
        field.sendKeys(query);
        field.sendKeys(Keys.ENTER);

        wait.until(
                driver -> driver.getCurrentUrl().contains("q=")
        );
    }

    public boolean isIssueFound(String issueId) {
        By locator = By.xpath(
                String.format(foundIssueId, issueId)
        );

        return isDisplayed(locator);
    }

    public boolean isIssuesPageOpened() {
        return isDisplayed(newIssueButton);
    }
}
