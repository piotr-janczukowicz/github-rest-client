package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetGithubBillingUsageReportUserFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetGithubBillingUsageReportUserFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetGithubBillingUsageReportUserFilter filter =
                BillingCatalogFixtures.getGithubBillingUsageReportUserFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("year", "2026", "month", "8", "day", "1"));
    }
}
