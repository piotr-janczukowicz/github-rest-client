package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMarketplaceListingPlanTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubMarketplaceListingPlanBuilder builder = GitHubMarketplaceListingPlanBuilder.builder(
                        AppsCatalogFixtures.marketplaceListingPlan())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenAccountsUrlNull() {
        final GitHubMarketplaceListingPlanBuilder builder = GitHubMarketplaceListingPlanBuilder.builder(
                        AppsCatalogFixtures.marketplaceListingPlan())
                .accountsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("accountsUrl");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubMarketplaceListingPlanBuilder builder = GitHubMarketplaceListingPlanBuilder.builder(
                        AppsCatalogFixtures.marketplaceListingPlan())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubMarketplaceListingPlanBuilder builder = GitHubMarketplaceListingPlanBuilder.builder(
                        AppsCatalogFixtures.marketplaceListingPlan())
                .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenPriceModelNull() {
        final GitHubMarketplaceListingPlanBuilder builder = GitHubMarketplaceListingPlanBuilder.builder(
                        AppsCatalogFixtures.marketplaceListingPlan())
                .priceModel(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("priceModel");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubMarketplaceListingPlanBuilder builder = GitHubMarketplaceListingPlanBuilder.builder(
                        AppsCatalogFixtures.marketplaceListingPlan())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenBulletsNull() {
        final GitHubMarketplaceListingPlanBuilder builder = GitHubMarketplaceListingPlanBuilder.builder(
                        AppsCatalogFixtures.marketplaceListingPlan())
                .bullets(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("bullets");
    }
}
