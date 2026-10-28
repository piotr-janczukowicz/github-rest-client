package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommitTest {

    @Test
    void compactConstructor_shouldThrow_whenParentsNull() {
        final GitHubCommitDetail detail = RepositoriesCatalogFixtures.commitDetail();

        final Throwable thrown = catchThrowable(
                () -> new GitHubCommit("url", "sha", "node", "html", "comments", detail, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("parents");
    }
}
