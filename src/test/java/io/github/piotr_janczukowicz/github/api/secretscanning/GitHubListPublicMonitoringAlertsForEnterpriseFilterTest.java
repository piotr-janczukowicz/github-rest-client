package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPublicMonitoringAlertsForEnterpriseFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListPublicMonitoringAlertsForEnterpriseFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField_whenAllSet() {
        final GitHubListPublicMonitoringAlertsForEnterpriseFilter filter =
                SecretScanningCatalogFixtures.listPublicMonitoringAlertsForEnterpriseFilter();

        assertThat(filter.toQueryParameters())
                .containsEntry("direction", "asc")
                .containsEntry("repository", "octo-org/hello")
                .containsEntry("secret_author", "octocat")
                .containsEntry("secret_type", "github_personal_access_token")
                .containsEntry("attribution", "member_activity")
                .containsEntry("validity", "active");
    }

    @Test
    void toString_shouldShowTheFilterValues_whenCalled() {
        final GitHubListPublicMonitoringAlertsForEnterpriseFilter filter =
                SecretScanningCatalogFixtures.listPublicMonitoringAlertsForEnterpriseFilter();

        assertThat(filter.toString()).contains("secretAuthor=octocat").contains("attribution=MEMBER_ACTIVITY");
    }
}
