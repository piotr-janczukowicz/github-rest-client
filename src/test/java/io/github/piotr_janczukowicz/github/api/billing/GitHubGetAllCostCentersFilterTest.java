package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllCostCentersFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubGetAllCostCentersFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubGetAllCostCentersFilter filter = BillingCatalogFixtures.getAllCostCentersFilter();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("state", "active"));
    }
}
