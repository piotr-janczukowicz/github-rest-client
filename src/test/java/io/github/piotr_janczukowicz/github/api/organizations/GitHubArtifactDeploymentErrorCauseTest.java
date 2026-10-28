package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubArtifactDeploymentErrorCauseTest {

    @ParameterizedTest
    @CsvSource({"unauthorized,UNAUTHORIZED", "not_found,NOT_FOUND"})
    void fromWire_shouldReturnCause_whenKnownValue(final String wire, final GitHubArtifactDeploymentErrorCause cause) {
        final GitHubArtifactDeploymentErrorCause decoded = GitHubArtifactDeploymentErrorCause.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(cause);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubArtifactDeploymentErrorCause.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubArtifactDeploymentErrorCause decoded =
                GitHubArtifactDeploymentErrorCause.fromWire("brand_new_value");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubArtifactDeploymentErrorCause.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
