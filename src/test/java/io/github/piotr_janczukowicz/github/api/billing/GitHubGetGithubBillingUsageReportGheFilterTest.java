package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubBillingUsageReportGheFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetGithubBillingUsageReportGheFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetGithubBillingUsageReportGheFilter filter =
                BillingCatalogFixtures.getGithubBillingUsageReportGheFilter();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of("year", "2026", "month", "8", "day", "1", "cost_center_id", "cc-1a2b3c"));
    }
}
