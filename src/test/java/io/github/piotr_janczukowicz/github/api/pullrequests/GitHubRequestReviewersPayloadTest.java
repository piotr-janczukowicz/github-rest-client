package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRequestReviewersPayloadTest {

    @Test
    void constructor_shouldThrow_whenNeitherListSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubRequestReviewersPayload(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least one of reviewers or teamReviewers is required");
    }

    @Test
    void constructor_shouldThrow_whenBothListsEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubRequestReviewersPayload(List.of(), List.of()));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least one of reviewers or teamReviewers is required");
    }

    @Test
    void constructor_shouldThrow_whenReviewersEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubRequestReviewersPayload(List.of(), null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reviewers");
    }

    @Test
    void constructor_shouldThrow_whenTeamReviewersEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubRequestReviewersPayload(null, List.of()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("teamReviewers");
    }

    @Test
    void constructor_shouldSucceed_whenReviewersEmptyAndTeamReviewersNonEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubRequestReviewersPayload(List.of(), List.of("team-a")));

        assertThat(thrown).isNull();
    }

    @Test
    void constructor_shouldSucceed_whenReviewersNonEmptyAndTeamReviewersEmpty() {
        final Throwable thrown = catchThrowable(() -> new GitHubRequestReviewersPayload(List.of("octocat"), List.of()));

        assertThat(thrown).isNull();
    }
}
