package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDelegatedBypassReviewerTest {

    @Test
    void constructor_shouldThrow_whenReviewerTypeNull() {
        final GitHubSecretScanningDelegatedBypassReviewer base = new GitHubSecretScanningDelegatedBypassReviewer(
                1L, GitHubSecretScanningBypassReviewerType.TEAM, GitHubSecretScanningDelegatedBypassMode.ALWAYS);

        final Throwable thrown = catchThrowable(
                () -> new GitHubSecretScanningDelegatedBypassReviewer(base.reviewerId(), null, base.mode()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reviewerType");
    }
}
