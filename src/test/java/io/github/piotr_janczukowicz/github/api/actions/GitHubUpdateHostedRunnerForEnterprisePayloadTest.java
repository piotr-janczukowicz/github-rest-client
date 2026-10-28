package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateHostedRunnerForEnterprisePayloadTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubUpdateHostedRunnerForEnterprisePayload value =
                ActionsCatalogFixtures.updateHostedRunnerForEnterprisePayload();

        assertThat(value.name()).isEqualTo("name-sample");
        assertThat(value.runnerGroupId()).isEqualTo(1L);
        assertThat(value.maximumRunners()).isEqualTo(1);
        assertThat(value.enableStaticIp()).isTrue();
        assertThat(value.size()).isEqualTo("size-sample");
        assertThat(value.imageSource()).isEqualTo(GitHubUpdateHostedRunnerEnterpriseImageSource.GITHUB);
        assertThat(value.imageId()).isEqualTo("imageId-sample");
        assertThat(value.imageVersion()).isEqualTo("imageVersion-sample");
        assertThat(value.imageGen()).isTrue();
    }
}
