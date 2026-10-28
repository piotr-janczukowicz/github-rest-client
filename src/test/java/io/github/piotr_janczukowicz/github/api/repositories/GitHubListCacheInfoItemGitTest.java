package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCacheInfoItemGitTest {

    @Test
    void constructor_shouldThrow_whenSyncStatusNull() {
        final GitHubListCacheInfoItemGitBuilder builder = GitHubListCacheInfoItemGitBuilder.builder(
                        RepositoriesCatalogFixtures.listCacheInfoItemGit())
                .syncStatus(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("syncStatus");
    }

    @Test
    void constructor_shouldThrow_whenLastSyncNull() {
        final GitHubListCacheInfoItemGitBuilder builder = GitHubListCacheInfoItemGitBuilder.builder(
                        RepositoriesCatalogFixtures.listCacheInfoItemGit())
                .lastSync(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("lastSync");
    }
}
