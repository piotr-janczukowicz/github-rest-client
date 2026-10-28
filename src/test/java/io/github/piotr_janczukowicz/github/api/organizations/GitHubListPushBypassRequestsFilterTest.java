package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPushBypassRequestsFilterTest {

    @Test
    void empty_shouldHaveNoQueryParameters() {
        assertThat(GitHubListPushBypassRequestsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEverySet_whenAllSet() {
        final Map<String, String> query =
                OrganizationsCatalogFixtures.listPushBypassRequestsFilter().toQueryParameters();

        assertThat(query)
                .hasSize(5)
                .containsEntry("repository_name", "octo-repo")
                .containsEntry("reviewer", "octocat")
                .containsEntry("requester", "hubot")
                .containsEntry("time_period", "week")
                .containsEntry("request_status", "open");
    }
}
