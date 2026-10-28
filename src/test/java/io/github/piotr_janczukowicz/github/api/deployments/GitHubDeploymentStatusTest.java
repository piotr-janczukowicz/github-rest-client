package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentStatusTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() -> status(null, "ok"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionTooLong() {
        final Throwable thrown = catchThrowable(() -> status(GitHubDeploymentStatusState.SUCCESS, "x".repeat(141)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }

    private static GitHubDeploymentStatus status(final GitHubDeploymentStatusState state, final String description) {
        return new GitHubDeploymentStatus(
                "https://example.com/status",
                1L,
                "n",
                state,
                null,
                description,
                null,
                "https://example.com/target",
                Instant.parse("2012-07-20T01:19:13Z"),
                Instant.parse("2012-07-20T01:19:13Z"),
                "https://example.com/deployment",
                "https://example.com/repo",
                null,
                null,
                null);
    }
}
