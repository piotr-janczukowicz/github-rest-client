package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateHostedRunnerForOrgPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final GitHubCreateHostedRunnerImage image = ActionsCatalogFixtures.createHostedRunnerImage();
        final String size = "size-sample";
        final int runnerGroupId = 1;
        final Integer maximumRunners = 1;
        final Boolean enableStaticIp = true;
        final Boolean imageGen = true;

        final Throwable thrown = catchThrowable(() -> new GitHubCreateHostedRunnerForOrgPayload(
                null, image, size, runnerGroupId, maximumRunners, enableStaticIp, imageGen));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenImageNull() {
        final String name = "name-sample";
        final String size = "size-sample";
        final int runnerGroupId = 1;
        final Integer maximumRunners = 1;
        final Boolean enableStaticIp = true;
        final Boolean imageGen = true;

        final Throwable thrown = catchThrowable(() -> new GitHubCreateHostedRunnerForOrgPayload(
                name, null, size, runnerGroupId, maximumRunners, enableStaticIp, imageGen));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("image");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenSizeNull() {
        final String name = "name-sample";
        final GitHubCreateHostedRunnerImage image = ActionsCatalogFixtures.createHostedRunnerImage();
        final int runnerGroupId = 1;
        final Integer maximumRunners = 1;
        final Boolean enableStaticIp = true;
        final Boolean imageGen = true;

        final Throwable thrown = catchThrowable(() -> new GitHubCreateHostedRunnerForOrgPayload(
                name, image, null, runnerGroupId, maximumRunners, enableStaticIp, imageGen));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("size");
    }
}
