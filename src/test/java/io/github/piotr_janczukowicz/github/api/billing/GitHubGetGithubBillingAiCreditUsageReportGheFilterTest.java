package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubBillingAiCreditUsageReportGheFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetGithubBillingAiCreditUsageReportGheFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetGithubBillingAiCreditUsageReportGheFilter filter =
                BillingCatalogFixtures.getGithubBillingAiCreditUsageReportGheFilter();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "year",
                        "2026",
                        "month",
                        "8",
                        "day",
                        "1",
                        "organization",
                        "octo-org",
                        "user",
                        "octocat",
                        "model",
                        "gpt-5",
                        "product",
                        "copilot",
                        "cost_center_id",
                        "cc-1a2b3c"));
    }
}
