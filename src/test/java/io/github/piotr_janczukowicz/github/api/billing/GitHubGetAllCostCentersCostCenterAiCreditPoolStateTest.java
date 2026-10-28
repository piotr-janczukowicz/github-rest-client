package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllCostCentersCostCenterAiCreditPoolStateTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.getAllCostCentersCostCenterAiCreditPoolState())
                .isNotNull();
    }
}
