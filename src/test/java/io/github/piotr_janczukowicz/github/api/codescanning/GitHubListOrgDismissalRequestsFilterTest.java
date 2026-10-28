package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListOrgDismissalRequestsFilterTest {

    @Test
    void empty_shouldOmitQueryParameters() {
        final GitHubListOrgDismissalRequestsFilter filter = GitHubListOrgDismissalRequestsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEveryQuery_whenSet() {
        final GitHubListOrgDismissalRequestsFilter filter =
                CodeScanningCatalogFixtures.listOrgDismissalRequestsFilter();

        assertThat(filter.toQueryParameters())
                .containsExactly(
                        Map.entry("reviewer", "octocat"),
                        Map.entry("requester", "hubot"),
                        Map.entry("time_period", "week"),
                        Map.entry("request_status", "open"),
                        Map.entry("repository_name", "octo-repo"));
    }
}
