package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitCommitUserTest {

    @Test
    void constructor_shouldThrow_whenDateNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitUserBuilder.builder(GitDatabaseCatalogFixtures.gitCommitUser())
                        .date(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("date");
    }

    @Test
    void constructor_shouldThrow_whenEmailNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitUserBuilder.builder(GitDatabaseCatalogFixtures.gitCommitUser())
                        .email(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("email");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitUserBuilder.builder(GitDatabaseCatalogFixtures.gitCommitUser())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
