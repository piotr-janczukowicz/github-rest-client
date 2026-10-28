package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllBudgetsOrgFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetAllBudgetsOrgFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetAllBudgetsOrgFilter filter = BillingCatalogFixtures.getAllBudgetsOrgFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("scope", "enterprise", "user", "octocat"));
    }
}
