package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCostCenterPayloadTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.updateCostCenterPayload()).isNotNull();
    }

    @Test
    void constructor_shouldAccept_whenOnlyLastGroupGiven() {
        final GitHubUpdateCostCenterPayload value = GitHubUpdateCostCenterPayloadBuilder.builder(
                        BillingCatalogFixtures.updateCostCenterPayload())
                .name(null)
                .build();

        assertThat(value.aiCreditPoolEnabled()).isNotNull();
    }

    @Test
    void constructor_shouldThrow_whenNothingGiven() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCostCenterPayloadBuilder.builder(BillingCatalogFixtures.updateCostCenterPayload())
                        .name(null)
                        .aiCreditPoolEnabled(null)
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
