package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGenerateRunnerJitconfigForEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubGenerateRunnerJitconfigForEnterprisePayload(null, 1, List.of("labels-sample"), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubGenerateRunnerJitconfigForEnterprisePayload value =
                ActionsCatalogFixtures.generateRunnerJitconfigForEnterprisePayload();

        assertThat(value.name()).isEqualTo("name-sample");
        assertThat(value.runnerGroupId()).isEqualTo(1L);
        assertThat(value.labels()).containsExactly("labels-sample");
        assertThat(value.workFolder()).isEqualTo("workFolder-sample");
    }
}
