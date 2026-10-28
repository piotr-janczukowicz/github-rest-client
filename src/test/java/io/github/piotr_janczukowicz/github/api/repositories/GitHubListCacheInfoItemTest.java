package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCacheInfoItemTest {

    @Test
    void constructor_shouldThrow_whenHostNull() {
        final GitHubListCacheInfoItemBuilder builder = GitHubListCacheInfoItemBuilder.builder(
                        RepositoriesCatalogFixtures.listCacheInfoItem())
                .host(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("host");
    }

    @Test
    void constructor_shouldThrow_whenLocationNull() {
        final GitHubListCacheInfoItemBuilder builder = GitHubListCacheInfoItemBuilder.builder(
                        RepositoriesCatalogFixtures.listCacheInfoItem())
                .location(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("location");
    }

    @Test
    void constructor_shouldThrow_whenGitNull() {
        final GitHubListCacheInfoItemBuilder builder = GitHubListCacheInfoItemBuilder.builder(
                        RepositoriesCatalogFixtures.listCacheInfoItem())
                .git(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("git");
    }
}
