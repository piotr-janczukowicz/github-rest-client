package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueArtifactTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubIssueArtifactBuilder builder = GitHubIssueArtifactBuilder.builder(
                        IssuesCatalogFixtures.issueArtifact())
                .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubIssueArtifactBuilder builder = GitHubIssueArtifactBuilder.builder(
                        IssuesCatalogFixtures.issueArtifact())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubIssueArtifactBuilder builder = GitHubIssueArtifactBuilder.builder(
                        IssuesCatalogFixtures.issueArtifact())
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubIssueArtifactBuilder builder = GitHubIssueArtifactBuilder.builder(
                        IssuesCatalogFixtures.issueArtifact())
                .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }
}
