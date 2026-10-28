package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAlertsForOrgFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubListAlertsForOrgFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubListAlertsForOrgFilter filter = GitHubListAlertsForOrgFilterBuilder.builder()
                .toolName("CodeQL")
                .toolGuid("0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d")
                .direction(GitHubDirection.DESC)
                .state(GitHubCodeScanningAlertStateQuery.OPEN)
                .sort(GitHubListAlertsForOrgSort.CREATED)
                .severity(GitHubCodeScanningAlertSeverity.CRITICAL)
                .assignees("assignees")
                .build();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "tool_name", "CodeQL",
                        "tool_guid", "0a9b3c7d-e8f1-4b2a-9c3d-5e6f7a8b9c0d",
                        "direction", "desc",
                        "state", "open",
                        "sort", "created",
                        "severity", "critical",
                        "assignees", "assignees"));
    }
}
