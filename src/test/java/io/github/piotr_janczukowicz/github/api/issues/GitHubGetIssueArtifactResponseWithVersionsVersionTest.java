package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetIssueArtifactResponseWithVersionsVersionTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubGetIssueArtifactResponseWithVersionsVersionBuilder builder =
                GitHubGetIssueArtifactResponseWithVersionsVersionBuilder.builder(
                                IssuesCatalogFixtures.getIssueArtifactResponseWithVersionsVersion())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubGetIssueArtifactResponseWithVersionsVersionBuilder builder =
                GitHubGetIssueArtifactResponseWithVersionsVersionBuilder.builder(
                                IssuesCatalogFixtures.getIssueArtifactResponseWithVersionsVersion())
                        .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }
}
