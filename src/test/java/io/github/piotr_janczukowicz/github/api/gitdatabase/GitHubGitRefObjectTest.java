package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitRefObjectTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitRefObjectBuilder.builder(GitDatabaseCatalogFixtures.gitRefObject())
                        .type(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitRefObjectBuilder.builder(GitDatabaseCatalogFixtures.gitRefObject())
                        .sha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenShaShorterThan40() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitRefObjectBuilder.builder(GitDatabaseCatalogFixtures.gitRefObject())
                        .sha("abc")
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenShaLongerThan40() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitRefObjectBuilder.builder(GitDatabaseCatalogFixtures.gitRefObject())
                        .sha("a".repeat(41))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitRefObjectBuilder.builder(GitDatabaseCatalogFixtures.gitRefObject())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
