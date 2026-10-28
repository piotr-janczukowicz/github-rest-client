package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListDismissalRequestsForRepoFilterTest {

    @Test
    void empty_shouldOmitQueryParameters() {
        final GitHubListDismissalRequestsForRepoFilter filter = GitHubListDismissalRequestsForRepoFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEveryQuery_whenSet() {
        final GitHubListDismissalRequestsForRepoFilter filter =
                CodeScanningCatalogFixtures.listDismissalRequestsForRepoFilter();

        assertThat(filter.toQueryParameters())
                .containsExactly(
                        Map.entry("reviewer", "octocat"),
                        Map.entry("requester", "hubot"),
                        Map.entry("time_period", "day"),
                        Map.entry("request_status", "approved"));
    }
}
