package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMarketplaceAccountTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubMarketplaceAccountBuilder builder = GitHubMarketplaceAccountBuilder.builder(
                        AppsCatalogFixtures.marketplaceAccount())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubMarketplaceAccountBuilder builder = GitHubMarketplaceAccountBuilder.builder(
                        AppsCatalogFixtures.marketplaceAccount())
                .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubMarketplaceAccountBuilder builder = GitHubMarketplaceAccountBuilder.builder(
                        AppsCatalogFixtures.marketplaceAccount())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }
}
