package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IssuesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By summaryField =
            By.cssSelector("textarea[data-test='summary']");

    private final By descriptionField =
            By.cssSelector("div[data-test='wysiwyg-editor-content']");

    private  final  By createButton =
            By.cssSelector("button[data-test='submit-button']");

    private final By newIssueButton =
            By.cssSelector("[data-test='createIssueButton']");

    private final By issuesTab =
            By.linkText("Issues");

    private final By issueSummary =
            By.cssSelector("h1[data-test='ticket-summary']");

    private final By searchField =
            By.cssSelector("input[data-test='ring-select__focus']");

    private final By foundIssueId =
            By.cssSelector("a[data-test='ring-link ticket-id']");

    public IssuesPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void openIssuesTab() {
        //System.out.println("До перехода: " + driver.getCurrentUrl());

        WebElement tab = wait.until(
                ExpectedConditions.elementToBeClickable(issuesTab)
        );

        //System.out.println("Нашли ссылку Issues: " + tab.getText());
        //System.out.println("Ссылка ведёт на: " + tab.getAttribute("href"));

        tab.click();

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
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(newIssueButton)
        );
        button.click();
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
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(createButton)
        );
        button.click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(issueSummary)
        );

        //System.out.println("Текущий URL: " + driver.getCurrentUrl());
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

//        System.out.println(
//                "Текст в поле поиска: " + field.getAttribute("value")
//        );

        field.sendKeys(Keys.ENTER);

        wait.until(
                driver -> driver.getCurrentUrl().contains("q=")
        );

//        System.out.println(
//                "URL после Enter: " + driver.getCurrentUrl()
//        );
    }

    public boolean isIssueFound(String issueId) {
        By issueLink = By.xpath(
                "//a[@data-test='ring-link ticket-id' and text()='" + issueId + "']"
        );

        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(issueLink)
            );
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isIssuesPageOpened() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(newIssueButton)
        ).isDisplayed();
    }
}
