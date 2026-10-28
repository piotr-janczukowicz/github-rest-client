package io.github.piotr_janczukowicz.github.api.releases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReleaseNotesContentTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReleaseNotesContent(null, "body"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReleaseNotesContent("name", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }
}
