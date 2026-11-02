package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateIssueArtifactPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateIssueArtifactPayloadBuilder builder = GitHubCreateIssueArtifactPayloadBuilder.builder(
                        IssuesCatalogFixtures.createIssueArtifactPayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubCreateIssueArtifactPayloadBuilder builder = GitHubCreateIssueArtifactPayloadBuilder.builder(
                        IssuesCatalogFixtures.createIssueArtifactPayload())
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenNameTooShort() {
        final GitHubCreateIssueArtifactPayloadBuilder builder = GitHubCreateIssueArtifactPayloadBuilder.builder(
                        IssuesCatalogFixtures.createIssueArtifactPayload())
                .name("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBodyTooShort() {
        final GitHubCreateIssueArtifactPayloadBuilder builder = GitHubCreateIssueArtifactPayloadBuilder.builder(
                        IssuesCatalogFixtures.createIssueArtifactPayload())
                .body("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }
}
