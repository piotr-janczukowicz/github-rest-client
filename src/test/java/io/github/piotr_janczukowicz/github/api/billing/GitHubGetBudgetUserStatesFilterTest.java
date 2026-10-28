package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetBudgetUserStatesFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetBudgetUserStatesFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetBudgetUserStatesFilter filter = BillingCatalogFixtures.getBudgetUserStatesFilter();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "sort_order",
                        "1",
                        "user",
                        "octocat",
                        "threshold_lower_bound",
                        "10",
                        "threshold_upper_bound",
                        "90"));
    }
}
