package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeployment(
                null,
                1L,
                "n",
                "sha",
                "ref",
                "deploy",
                GitHubDeploymentPayloadValue.ofText("{}"),
                null,
                "production",
                null,
                null,
                Instant.parse("2012-07-20T01:19:13Z"),
                Instant.parse("2012-07-20T01:19:13Z"),
                "https://example.com/statuses",
                "https://example.com/repo",
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
