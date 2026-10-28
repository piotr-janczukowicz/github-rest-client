package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubListAutomationTasksTaskArtifactTypeTest {

    @ParameterizedTest
    @CsvSource({"pull,PULL", "branch,BRANCH"})
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubListAutomationTasksTaskArtifactType value) {
        final GitHubListAutomationTasksTaskArtifactType decoded =
                GitHubListAutomationTasksTaskArtifactType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListAutomationTasksTaskArtifactType.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubListAutomationTasksTaskArtifactType decoded =
                GitHubListAutomationTasksTaskArtifactType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubListAutomationTasksTaskArtifactType.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
