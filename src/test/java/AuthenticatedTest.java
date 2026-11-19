import org.example.pages.IssuesPage;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;

public class AuthenticatedTest extends  BaseTest{
    protected IssuesPage issuesPage;

    @BeforeEach
    public void loginBeforeTest(){
        LoginPage  loginPage = new LoginPage(driver);
        loginPage.login("admin", "password");

        issuesPage = new IssuesPage(driver);
        issuesPage.openIssuesTab();
    }
}
