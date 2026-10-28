package io.github.piotr_janczukowicz.github.api.releases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubReleaseAssetStateTest {

    @ParameterizedTest
    @CsvSource({"uploaded,UPLOADED", "open,OPEN"})
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubReleaseAssetState value) {
        final GitHubReleaseAssetState decoded = GitHubReleaseAssetState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubReleaseAssetState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubReleaseAssetState decoded = GitHubReleaseAssetState.fromWire("brand_new_value");

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubReleaseAssetState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
