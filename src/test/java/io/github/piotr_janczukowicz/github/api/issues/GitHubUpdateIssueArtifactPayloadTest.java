package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateIssueArtifactPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameTooShort() {
        final GitHubUpdateIssueArtifactPayloadBuilder builder = GitHubUpdateIssueArtifactPayloadBuilder.builder(
                        IssuesCatalogFixtures.updateIssueArtifactPayload())
                .name("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBodyTooShort() {
        final GitHubUpdateIssueArtifactPayloadBuilder builder = GitHubUpdateIssueArtifactPayloadBuilder.builder(
                        IssuesCatalogFixtures.updateIssueArtifactPayload())
                .body("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenNameAndBodyNull() {
        final GitHubUpdateIssueArtifactPayloadBuilder builder = GitHubUpdateIssueArtifactPayloadBuilder.builder(
                        IssuesCatalogFixtures.updateIssueArtifactPayload())
                .name(null)
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name or body");
    }
}
