package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRemoveReviewersPayloadTest {

    @Test
    void constructor_shouldThrow_whenReviewersNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRemoveReviewersPayload(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reviewers");
    }

    @Test
    void constructor_shouldAcceptEmptyReviewers_whenTeamReviewersPresent() {
        final GitHubRemoveReviewersPayload payload =
                new GitHubRemoveReviewersPayload(List.of(), List.of("justice-league"));

        assertSoftly(softly -> {
            softly.assertThat(payload.reviewers()).isEmpty();
            softly.assertThat(payload.teamReviewers()).containsExactly("justice-league");
        });
    }

    @Test
    void constructor_shouldLeaveTeamReviewersNull_whenTeamReviewersNull() {
        final GitHubRemoveReviewersPayload payload = new GitHubRemoveReviewersPayload(List.of("octocat"), null);

        assertSoftly(softly -> {
            softly.assertThat(payload.reviewers()).containsExactly("octocat");
            softly.assertThat(payload.teamReviewers()).isNull();
        });
    }
}
