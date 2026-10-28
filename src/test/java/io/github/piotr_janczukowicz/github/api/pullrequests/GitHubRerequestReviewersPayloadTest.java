package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRerequestReviewersPayloadTest {

    @Test
    void constructor_shouldThrow_whenNeitherListSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubRerequestReviewersPayload(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least one of reviewers or teamReviewers is required");
    }

    @Test
    void constructor_shouldThrow_whenBothListsEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubRerequestReviewersPayload(List.of(), List.of()));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least one of reviewers or teamReviewers is required");
    }

    @Test
    void constructor_shouldSucceed_whenReviewersEmptyAndTeamReviewersNonEmpty() {
        final GitHubRerequestReviewersPayload payload =
                new GitHubRerequestReviewersPayload(List.of(), List.of("team-a"));

        assertThat(payload.teamReviewers()).containsExactly("team-a");
    }

    @Test
    void constructor_shouldSucceed_whenReviewersNonEmptyAndTeamReviewersNull() {
        final GitHubRerequestReviewersPayload payload = new GitHubRerequestReviewersPayload(List.of("octocat"), null);

        assertThat(payload.teamReviewers()).isEmpty();
    }
}
