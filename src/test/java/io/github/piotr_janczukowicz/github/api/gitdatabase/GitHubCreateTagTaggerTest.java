package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateTagTaggerTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateTagTaggerBuilder.builder(GitDatabaseCatalogFixtures.createTagTagger())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenEmailNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateTagTaggerBuilder.builder(GitDatabaseCatalogFixtures.createTagTagger())
                        .email(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("email");
    }
}
