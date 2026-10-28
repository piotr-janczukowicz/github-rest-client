package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsHostedRunnerCustomImageTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenPlatformNull() {
        final int id = 1;
        final int totalVersionsSize = 1;
        final String name = "name-sample";
        final String source = "source-sample";
        final int versionsCount = 1;
        final String latestVersion = "latestVersion-sample";
        final String state = "state-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnerCustomImage(
                id, null, totalVersionsSize, name, source, versionsCount, latestVersion, state));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("platform");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final int id = 1;
        final String platform = "platform-sample";
        final int totalVersionsSize = 1;
        final String source = "source-sample";
        final int versionsCount = 1;
        final String latestVersion = "latestVersion-sample";
        final String state = "state-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnerCustomImage(
                id, platform, totalVersionsSize, null, source, versionsCount, latestVersion, state));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenSourceNull() {
        final int id = 1;
        final String platform = "platform-sample";
        final int totalVersionsSize = 1;
        final String name = "name-sample";
        final int versionsCount = 1;
        final String latestVersion = "latestVersion-sample";
        final String state = "state-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnerCustomImage(
                id, platform, totalVersionsSize, name, null, versionsCount, latestVersion, state));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("source");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenLatestVersionNull() {
        final int id = 1;
        final String platform = "platform-sample";
        final int totalVersionsSize = 1;
        final String name = "name-sample";
        final String source = "source-sample";
        final int versionsCount = 1;
        final String state = "state-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnerCustomImage(
                id, platform, totalVersionsSize, name, source, versionsCount, null, state));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("latestVersion");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStateNull() {
        final int id = 1;
        final String platform = "platform-sample";
        final int totalVersionsSize = 1;
        final String name = "name-sample";
        final String source = "source-sample";
        final int versionsCount = 1;
        final String latestVersion = "latestVersion-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnerCustomImage(
                id, platform, totalVersionsSize, name, source, versionsCount, latestVersion, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }
}
