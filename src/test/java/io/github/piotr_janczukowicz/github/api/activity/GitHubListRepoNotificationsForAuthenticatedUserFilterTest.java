package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoNotificationsForAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubListRepoNotificationsForAuthenticatedUserFilter filter =
                GitHubListRepoNotificationsForAuthenticatedUserFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFields_whenFilterSet() {
        final GitHubListRepoNotificationsForAuthenticatedUserFilter filter =
                ActivityCatalogFixtures.listRepoNotificationsFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsOnly(
                        Map.entry("all", "true"),
                        Map.entry("participating", "false"),
                        Map.entry("since", "2024-01-01T00:00:00Z"),
                        Map.entry("before", "2024-06-01T00:00:00Z"));
    }
}
