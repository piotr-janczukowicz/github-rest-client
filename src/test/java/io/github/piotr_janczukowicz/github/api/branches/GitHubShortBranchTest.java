package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubShortBranchTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubShortBranchCommit commit = new GitHubShortBranchCommit("abc", "https://example.com/commit");

        final Throwable thrown = catchThrowable(() -> new GitHubShortBranch(null, commit, true, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCommitNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubShortBranch("main", null, true, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commit");
    }
}
