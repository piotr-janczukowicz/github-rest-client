package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAlertsForRepoFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubListAlertsForRepoFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubListAlertsForRepoFilter filter = GitHubListAlertsForRepoFilterBuilder.builder()
                .toolName("CodeQL")
                .toolGuid("0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d")
                .ref("refs/heads/main")
                .pr(123)
                .direction(GitHubDirection.DESC)
                .sort(GitHubListAlertsForRepoSort.CREATED)
                .state(GitHubCodeScanningAlertStateQuery.OPEN)
                .severity(GitHubCodeScanningAlertSeverity.CRITICAL)
                .assignees("assignees")
                .build();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "tool_name", "CodeQL",
                        "tool_guid", "0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d",
                        "ref", "refs/heads/main",
                        "pr", "123",
                        "direction", "desc",
                        "sort", "created",
                        "state", "open",
                        "severity", "critical",
                        "assignees", "assignees"));
    }
}
