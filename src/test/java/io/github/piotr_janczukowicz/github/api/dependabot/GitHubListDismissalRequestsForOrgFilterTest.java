package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListDismissalRequestsForOrgFilterTest {

    @Test
    void empty_shouldOmitQueryParameters() {
        final GitHubListDismissalRequestsForOrgFilter filter = GitHubListDismissalRequestsForOrgFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEveryQuery_whenSet() {
        final GitHubListDismissalRequestsForOrgFilter filter =
                DependabotCatalogFixtures.listDismissalRequestsForOrgFilter();

        assertThat(filter.toQueryParameters())
                .containsExactly(
                        Map.entry("repository_name", "octo-repo"),
                        Map.entry("reviewer", "octocat"),
                        Map.entry("requester", "hubot"),
                        Map.entry("time_period", "day"),
                        Map.entry("request_status", "approved"));
    }
}
