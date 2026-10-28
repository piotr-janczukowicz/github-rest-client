package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDelegatedBypassOptionsTest {

    @Test
    void constructor_shouldKeepNullReviewers_whenReviewersNull() {
        final GitHubSecretScanningDelegatedBypassOptions options = new GitHubSecretScanningDelegatedBypassOptions(null);

        assertThat(options.reviewers()).isNull();
    }
}
