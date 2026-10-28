package io.github.piotr_janczukowicz.github.api.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePagesSiteSourceTest {

    @Test
    void constructor_shouldThrow_whenBranchNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreatePagesSiteSource(null, GitHubPagesSourcePath.ROOT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("branch");
    }
}
