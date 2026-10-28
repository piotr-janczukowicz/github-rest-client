package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubFileContentsAuthorTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubFileContentsAuthor(null, "octocat@github.com", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenEmailNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubFileContentsAuthor("Monalisa Octocat", null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("email");
    }
}
