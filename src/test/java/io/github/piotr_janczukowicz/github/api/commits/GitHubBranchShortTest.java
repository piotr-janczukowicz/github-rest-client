package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBranchShortTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubBranchShort(null, new GitHubBranchShortCommit("sha", "https://example.com"), false));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCommitNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBranchShort("main", null, false));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commit");
    }
}
