package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsHostedRunnerCustomImageVersionTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenVersionNull() {
        final String state = "state-sample";
        final int sizeGb = 1;
        final String createdOn = "createdOn-sample";
        final String stateDetails = "stateDetails-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsHostedRunnerCustomImageVersion(null, state, sizeGb, createdOn, stateDetails));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("version");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStateNull() {
        final String version = "version-sample";
        final int sizeGb = 1;
        final String createdOn = "createdOn-sample";
        final String stateDetails = "stateDetails-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsHostedRunnerCustomImageVersion(version, null, sizeGb, createdOn, stateDetails));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCreatedOnNull() {
        final String version = "version-sample";
        final String state = "state-sample";
        final int sizeGb = 1;
        final String stateDetails = "stateDetails-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsHostedRunnerCustomImageVersion(version, state, sizeGb, null, stateDetails));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdOn");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStateDetailsNull() {
        final String version = "version-sample";
        final String state = "state-sample";
        final int sizeGb = 1;
        final String createdOn = "createdOn-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsHostedRunnerCustomImageVersion(version, state, sizeGb, createdOn, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("stateDetails");
    }
}
