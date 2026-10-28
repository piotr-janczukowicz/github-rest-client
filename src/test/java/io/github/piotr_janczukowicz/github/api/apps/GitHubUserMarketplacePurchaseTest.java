package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserMarketplacePurchaseTest {

    @Test
    void constructor_shouldThrow_whenBillingCycleNull() {
        final GitHubUserMarketplacePurchaseBuilder builder = GitHubUserMarketplacePurchaseBuilder.builder(
                        AppsCatalogFixtures.userMarketplacePurchase())
                .billingCycle(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("billingCycle");
    }

    @Test
    void constructor_shouldThrow_whenAccountNull() {
        final GitHubUserMarketplacePurchaseBuilder builder = GitHubUserMarketplacePurchaseBuilder.builder(
                        AppsCatalogFixtures.userMarketplacePurchase())
                .account(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("account");
    }

    @Test
    void constructor_shouldThrow_whenPlanNull() {
        final GitHubUserMarketplacePurchaseBuilder builder = GitHubUserMarketplacePurchaseBuilder.builder(
                        AppsCatalogFixtures.userMarketplacePurchase())
                .plan(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("plan");
    }
}
