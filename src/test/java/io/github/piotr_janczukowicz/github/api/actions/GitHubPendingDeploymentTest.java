package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPendingDeploymentTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnvironmentNull() {
        final int waitTimer = 1;
        final Instant waitTimerStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final boolean currentUserCanApprove = true;
        final List<GitHubPendingDeploymentReviewerEntry> reviewers =
                List.of(ActionsCatalogFixtures.pendingDeploymentReviewerEntry());

        final Throwable thrown = catchThrowable(() ->
                new GitHubPendingDeployment(null, waitTimer, waitTimerStartedAt, currentUserCanApprove, reviewers));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environment");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenReviewersNull() {
        final GitHubPendingDeploymentEnvironment environment = ActionsCatalogFixtures.pendingDeploymentEnvironment();
        final int waitTimer = 1;
        final Instant waitTimerStartedAt = Instant.parse("2024-01-02T03:04:05Z");
        final boolean currentUserCanApprove = true;

        final Throwable thrown = catchThrowable(() ->
                new GitHubPendingDeployment(environment, waitTimer, waitTimerStartedAt, currentUserCanApprove, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reviewers");
    }
}
