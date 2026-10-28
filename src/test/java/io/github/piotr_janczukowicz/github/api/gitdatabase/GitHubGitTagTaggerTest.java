package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitTagTaggerTest {

    @Test
    void constructor_shouldThrow_whenDateNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTagTaggerBuilder.builder(GitDatabaseCatalogFixtures.gitTagTagger())
                        .date(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("date");
    }

    @Test
    void constructor_shouldThrow_whenEmailNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTagTaggerBuilder.builder(GitDatabaseCatalogFixtures.gitTagTagger())
                        .email(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("email");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTagTaggerBuilder.builder(GitDatabaseCatalogFixtures.gitTagTagger())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
