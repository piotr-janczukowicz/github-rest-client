package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubArtifactClusterJobStatusTest {

    @ParameterizedTest
    @CsvSource({"pending,PENDING", "processing,PROCESSING", "completed,COMPLETED", "failed,FAILED"})
    void fromWire_shouldReturnStatus_whenKnownValue(final String wire, final GitHubArtifactClusterJobStatus status) {
        final GitHubArtifactClusterJobStatus decoded = GitHubArtifactClusterJobStatus.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(status);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubArtifactClusterJobStatus.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubArtifactClusterJobStatus decoded = GitHubArtifactClusterJobStatus.fromWire("brand_new_value");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubArtifactClusterJobStatus.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
