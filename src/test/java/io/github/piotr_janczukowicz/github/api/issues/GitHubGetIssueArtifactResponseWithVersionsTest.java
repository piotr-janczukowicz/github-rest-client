package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetIssueArtifactResponseWithVersionsTest {

    @Test
    void constructor_shouldThrow_whenArtifactNull() {
        final GitHubGetIssueArtifactResponseWithVersionsBuilder builder =
                GitHubGetIssueArtifactResponseWithVersionsBuilder.builder(
                                IssuesCatalogFixtures.getIssueArtifactResponseWithVersions())
                        .artifact(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("artifact");
    }

    @Test
    void constructor_shouldThrow_whenVersionsNull() {
        final GitHubGetIssueArtifactResponseWithVersionsBuilder builder =
                GitHubGetIssueArtifactResponseWithVersionsBuilder.builder(
                                IssuesCatalogFixtures.getIssueArtifactResponseWithVersions())
                        .versions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("versions");
    }
}
