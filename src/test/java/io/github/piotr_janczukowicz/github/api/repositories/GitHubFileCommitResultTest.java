package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubFileCommitResultTest {

    @Test
    void constructor_shouldKeepNullParents_whenCalled() {
        final GitHubFileCommitResult result =
                new GitHubFileCommitResult("sha", "node", "url", "html", null, null, "message", null, null, null);

        assertThat(result.parents()).isNull();
    }
}
