package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateGistFileTest {
    @Test
    void constructor_shouldThrow_whenContentNull() {
        final GitHubCreateGistFileBuilder builder = GitHubCreateGistFileBuilder.builder(
                        GistsCatalogFixtures.createGistFile())
                .content(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("content");
    }
}
