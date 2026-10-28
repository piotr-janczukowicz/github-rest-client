package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllBudgetsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetAllBudgetsFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetAllBudgetsFilter filter = BillingCatalogFixtures.getAllBudgetsFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("scope", "enterprise", "user", "octocat"));
    }
}
