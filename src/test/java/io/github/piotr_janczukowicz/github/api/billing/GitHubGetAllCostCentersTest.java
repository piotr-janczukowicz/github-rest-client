package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllCostCentersTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.getAllCostCenters()).isNotNull();
    }

    @Test
    void constructor_shouldLeaveCostCentersNull_whenNull() {
        final GitHubGetAllCostCenters value = GitHubGetAllCostCentersBuilder.builder(
                        BillingCatalogFixtures.getAllCostCenters())
                .costCenters(null)
                .build();

        assertThat(value.costCenters()).isNull();
    }
}
