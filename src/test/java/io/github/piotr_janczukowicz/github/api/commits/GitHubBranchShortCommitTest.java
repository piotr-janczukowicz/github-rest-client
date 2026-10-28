package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBranchShortCommitTest {

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBranchShortCommit(null, "https://example.com"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBranchShortCommit("sha", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
