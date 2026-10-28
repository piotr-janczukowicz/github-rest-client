package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoPushBypassRequestsFilterTest {

    @Test
    void empty_shouldOmitQueryParameters_whenNoFieldsSet() {
        final GitHubListRepoPushBypassRequestsFilter filter = GitHubListRepoPushBypassRequestsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEveryQuery_whenSet() {
        final GitHubListRepoPushBypassRequestsFilter filter =
                RepositoriesCatalogFixtures.listRepoPushBypassRequestsFilter();

        assertThat(filter.toQueryParameters())
                .containsExactly(
                        Map.entry("reviewer", "octocat"),
                        Map.entry("requester", "hubot"),
                        Map.entry("time_period", "week"),
                        Map.entry("request_status", "open"));
    }
}
