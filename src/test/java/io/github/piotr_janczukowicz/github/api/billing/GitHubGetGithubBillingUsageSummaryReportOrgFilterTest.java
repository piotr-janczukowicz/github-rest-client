package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubBillingUsageSummaryReportOrgFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetGithubBillingUsageSummaryReportOrgFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetGithubBillingUsageSummaryReportOrgFilter filter =
                BillingCatalogFixtures.getGithubBillingUsageSummaryReportOrgFilter();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "year",
                        "2026",
                        "month",
                        "8",
                        "day",
                        "1",
                        "repository",
                        "octo-org/octo-repo",
                        "product",
                        "actions",
                        "sku",
                        "actions_linux"));
    }
}
