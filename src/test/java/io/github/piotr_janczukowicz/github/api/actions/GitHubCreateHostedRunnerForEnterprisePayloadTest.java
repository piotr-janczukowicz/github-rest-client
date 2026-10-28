package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateHostedRunnerForEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateHostedRunnerForEnterprisePayload(
                null, ActionsCatalogFixtures.createHostedRunnerEnterpriseImage(), "size-sample", 1, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenImageNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateHostedRunnerForEnterprisePayload(
                "name-sample", null, "size-sample", 1, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("image");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenSizeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateHostedRunnerForEnterprisePayload(
                "name-sample", ActionsCatalogFixtures.createHostedRunnerEnterpriseImage(), null, 1, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("size");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubCreateHostedRunnerForEnterprisePayload value =
                ActionsCatalogFixtures.createHostedRunnerForEnterprisePayload();

        assertThat(value.name()).isEqualTo("name-sample");
        assertThat(value.image()).isEqualTo(ActionsCatalogFixtures.createHostedRunnerEnterpriseImage());
        assertThat(value.size()).isEqualTo("size-sample");
        assertThat(value.runnerGroupId()).isEqualTo(1L);
        assertThat(value.maximumRunners()).isEqualTo(1);
        assertThat(value.enableStaticIp()).isTrue();
        assertThat(value.imageGen()).isTrue();
    }
}
