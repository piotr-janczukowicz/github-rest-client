package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateEnvironmentReviewerPayloadTest {

    @Test
    void constructor_shouldAllowNullType_whenAbsent() {
        final GitHubCreateOrUpdateEnvironmentReviewerPayload payload =
                new GitHubCreateOrUpdateEnvironmentReviewerPayload(null, 1L);

        assertThat(payload.type()).isNull();
        assertThat(payload.id()).isEqualTo(1);
    }
}
