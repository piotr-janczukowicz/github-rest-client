package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCustomLabelsForSelfHostedRunnerForEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenLabelsNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubSetCustomLabelsForSelfHostedRunnerForEnterprisePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("labels");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSetCustomLabelsForSelfHostedRunnerForEnterprisePayload value =
                ActionsCatalogFixtures.setCustomLabelsForSelfHostedRunnerForEnterprisePayload();

        assertThat(value.labels()).containsExactly("labels-sample");
    }
}
