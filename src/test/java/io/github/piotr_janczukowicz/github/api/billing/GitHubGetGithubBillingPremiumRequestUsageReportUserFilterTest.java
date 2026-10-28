package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubBillingPremiumRequestUsageReportUserFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetGithubBillingPremiumRequestUsageReportUserFilter.empty()
                        .toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetGithubBillingPremiumRequestUsageReportUserFilter filter =
                BillingCatalogFixtures.getGithubBillingPremiumRequestUsageReportUserFilter();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "year", "2026", "month", "8", "day", "1", "model", "Claude Sonnet", "product", "actions"));
    }
}
