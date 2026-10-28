package io.github.piotr_janczukowicz.github.api.interactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePullRequestCreationCapForOrgPayloadTest {

    @Test
    void constructor_shouldThrow_whenMaxOpenPullRequestsBelowMinimum() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubUpdatePullRequestCreationCapForOrgPayload(true, 0, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxOpenPullRequests");
    }

    @Test
    void constructor_shouldThrow_whenMaxOpenPullRequestsAboveMaximum() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubUpdatePullRequestCreationCapForOrgPayload(true, 1001, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxOpenPullRequests");
    }

    @Test
    void constructor_shouldLeaveMaxOpenPullRequestsNull_whenNull() {
        final GitHubUpdatePullRequestCreationCapForOrgPayload payload =
                new GitHubUpdatePullRequestCreationCapForOrgPayload(true, null, null);

        assertThat(payload.maxOpenPullRequests()).isNull();
    }

    @Test
    void constructor_shouldAcceptValue_whenAtDumpBounds() {
        assertThat(new GitHubUpdatePullRequestCreationCapForOrgPayload(true, 1, null).maxOpenPullRequests())
                .isEqualTo(1);
        assertThat(new GitHubUpdatePullRequestCreationCapForOrgPayload(false, 1000, null).maxOpenPullRequests())
                .isEqualTo(1000);
    }
}
