package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateForAuthenticatedUserPayloadTest {

    @Test
    void constructor_shouldThrow_whenRepositoryIdAndPullRequestBothAbsent() {
        final GitHubCreateForAuthenticatedUserPayloadBuilder builder =
                GitHubCreateForAuthenticatedUserPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createForAuthenticatedUserPayload())
                        .repositoryId(null)
                        .pullRequest(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryIdAndPullRequestBothPresent() {
        final GitHubCreateForAuthenticatedUserPayloadBuilder builder =
                GitHubCreateForAuthenticatedUserPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createForAuthenticatedUserPayload())
                        .pullRequest(CodespacesCatalogFixtures.codespacePullRequest());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void constructor_shouldAcceptPullRequestOnly_whenRepositoryIdAbsent() {
        final GitHubCreateForAuthenticatedUserPayloadBuilder builder =
                GitHubCreateForAuthenticatedUserPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createForAuthenticatedUserPayload())
                        .repositoryId(null)
                        .pullRequest(CodespacesCatalogFixtures.codespacePullRequest());

        final GitHubCreateForAuthenticatedUserPayload sut = builder.build();

        assertThat(sut.pullRequest()).isEqualTo(CodespacesCatalogFixtures.codespacePullRequest());
    }
}
