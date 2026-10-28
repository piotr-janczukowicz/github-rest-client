package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsHostedRunnerCuratedImageTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenIdNull() {
        final String platform = "platform-sample";
        final int sizeGb = 1;
        final String displayName = "displayName-sample";
        final GitHubHostedRunnerImageSource source = GitHubHostedRunnerImageSource.GITHUB;

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsHostedRunnerCuratedImage(null, platform, sizeGb, displayName, source));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPlatformNull() {
        final String id = "id-sample";
        final int sizeGb = 1;
        final String displayName = "displayName-sample";
        final GitHubHostedRunnerImageSource source = GitHubHostedRunnerImageSource.GITHUB;

        final Throwable thrown =
                catchThrowable(() -> new GitHubActionsHostedRunnerCuratedImage(id, null, sizeGb, displayName, source));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("platform");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenDisplayNameNull() {
        final String id = "id-sample";
        final String platform = "platform-sample";
        final int sizeGb = 1;
        final GitHubHostedRunnerImageSource source = GitHubHostedRunnerImageSource.GITHUB;

        final Throwable thrown =
                catchThrowable(() -> new GitHubActionsHostedRunnerCuratedImage(id, platform, sizeGb, null, source));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenSourceNull() {
        final String id = "id-sample";
        final String platform = "platform-sample";
        final int sizeGb = 1;
        final String displayName = "displayName-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsHostedRunnerCuratedImage(id, platform, sizeGb, displayName, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("source");
    }
}
