package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubBillingUsageReportOrgFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetGithubBillingUsageReportOrgFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetGithubBillingUsageReportOrgFilter filter =
                BillingCatalogFixtures.getGithubBillingUsageReportOrgFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("year", "2026", "month", "8", "day", "1"));
    }
}
