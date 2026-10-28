package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMarketplacePurchaseTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubMarketplacePurchaseBuilder builder = GitHubMarketplacePurchaseBuilder.builder(
                        AppsCatalogFixtures.marketplacePurchase())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubMarketplacePurchaseBuilder builder = GitHubMarketplacePurchaseBuilder.builder(
                        AppsCatalogFixtures.marketplacePurchase())
                .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubMarketplacePurchaseBuilder builder = GitHubMarketplacePurchaseBuilder.builder(
                        AppsCatalogFixtures.marketplacePurchase())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldThrow_whenMarketplacePurchaseNull() {
        final GitHubMarketplacePurchaseBuilder builder = GitHubMarketplacePurchaseBuilder.builder(
                        AppsCatalogFixtures.marketplacePurchase())
                .marketplacePurchase(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("marketplacePurchase");
    }
}
