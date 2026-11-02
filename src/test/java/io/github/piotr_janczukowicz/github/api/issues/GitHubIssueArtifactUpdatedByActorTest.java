package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueArtifactUpdatedByActorTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubIssueArtifactUpdatedByActorBuilder builder = GitHubIssueArtifactUpdatedByActorBuilder.builder(
                        IssuesCatalogFixtures.issueArtifactUpdatedByActor())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }
}
