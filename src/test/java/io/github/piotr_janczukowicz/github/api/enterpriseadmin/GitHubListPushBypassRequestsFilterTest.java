package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPushBypassRequestsFilterTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubListPushBypassRequestsFilter value = EnterpriseAdminCatalogFixtures.listPushBypassRequestsFilter();

        assertThat(GitHubListPushBypassRequestsFilterBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void empty_shouldHaveNoQueryParameters() {
        assertThat(GitHubListPushBypassRequestsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldContainEverySetField_whenFilled() {
        assertThat(EnterpriseAdminCatalogFixtures.listPushBypassRequestsFilter().toQueryParameters())
                .containsEntry("organization_name", "octo-org")
                .containsEntry("reviewer", "octocat")
                .containsEntry("requester", "hubot")
                .containsEntry("time_period", "week")
                .containsEntry("request_status", "open");
    }
}
