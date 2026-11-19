
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;


@Execution(ExecutionMode.SAME_THREAD)
public class IssueTest extends AuthenticatedTest {
    @Test
    public void testCreateIssue(){
        String summary = "Автотест: создание задачи " + UUID.randomUUID();
        String description = "Задача создана автоматически с помощью Selenium";

        issuesPage.openCreateIssueForm();
        issuesPage.enterSummary(summary);
        issuesPage.enterDescription(description);
        issuesPage.clickCreate();

        assertTrue(
                issuesPage.isIssueOpened(summary),
                "Страница созданной задачи не содержит название: " + summary
        );

        System.out.println("Задача успешно создана: " + summary);

    }

    @Test
    public void testSearchIssue() {
        String issueId = "QA-23";
        System.out.println("Ищем задачу: " + issueId);

        issuesPage.searchIssue("23");
        boolean found = issuesPage.isIssueFound(issueId);
        System.out.println("Задача найдена: " + found);

        assertTrue(
                found,
                "Задача " + issueId + " не найдена"
        );
    }
}
