package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewPendingDeploymentsForRunPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnvironmentIdsNull() {
        final GitHubReviewCustomGatesState state = GitHubReviewCustomGatesState.APPROVED;
        final String comment = "comment-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubReviewPendingDeploymentsForRunPayload(null, state, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environmentIds");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStateNull() {
        final List<Long> environmentIds = List.of(1L);
        final String comment = "comment-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubReviewPendingDeploymentsForRunPayload(environmentIds, null, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCommentNull() {
        final List<Long> environmentIds = List.of(1L);
        final GitHubReviewCustomGatesState state = GitHubReviewCustomGatesState.APPROVED;

        final Throwable thrown =
                catchThrowable(() -> new GitHubReviewPendingDeploymentsForRunPayload(environmentIds, state, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("comment");
    }
}
