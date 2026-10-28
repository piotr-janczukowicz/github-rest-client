package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitTagObjectTest {

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTagObjectBuilder.builder(GitDatabaseCatalogFixtures.gitTagObject())
                        .sha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTagObjectBuilder.builder(GitDatabaseCatalogFixtures.gitTagObject())
                        .type(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTagObjectBuilder.builder(GitDatabaseCatalogFixtures.gitTagObject())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
