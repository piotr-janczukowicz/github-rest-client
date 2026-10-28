package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsHostedRunnerPoolImageTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenIdNull() {
        final int sizeGb = 1;
        final String displayName = "displayName-sample";
        final GitHubHostedRunnerImageSource source = GitHubHostedRunnerImageSource.GITHUB;
        final String version = "version-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsHostedRunnerPoolImage(null, sizeGb, displayName, source, version));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenDisplayNameNull() {
        final String id = "id-sample";
        final int sizeGb = 1;
        final GitHubHostedRunnerImageSource source = GitHubHostedRunnerImageSource.GITHUB;
        final String version = "version-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubActionsHostedRunnerPoolImage(id, sizeGb, null, source, version));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenSourceNull() {
        final String id = "id-sample";
        final int sizeGb = 1;
        final String displayName = "displayName-sample";
        final String version = "version-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubActionsHostedRunnerPoolImage(id, sizeGb, displayName, null, version));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("source");
    }
}
