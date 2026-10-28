package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitCommitVerificationTest {

    @Test
    void constructor_shouldThrow_whenReasonNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubGitCommitVerificationBuilder.builder(GitDatabaseCatalogFixtures.gitCommitVerification())
                        .reason(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reason");
    }
}
