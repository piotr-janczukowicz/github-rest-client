package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubSecretScanningCustomPatternValidationErrorCodeTest {

    @ParameterizedTest
    @CsvSource({
        "invalid,INVALID",
        "unprocessable,UNPROCESSABLE",
        "start_delimiter,START_DELIMITER",
        "end_delimiter,END_DELIMITER",
        "name,NAME",
        "must_match,MUST_MATCH",
        "must_not_match,MUST_NOT_MATCH",
        "custom_pattern_version_mismatch,CUSTOM_PATTERN_VERSION_MISMATCH"
    })
    void fromWire_shouldReturnCode_whenKnownValue(
            final String wire, final GitHubSecretScanningCustomPatternValidationErrorCode value) {
        final GitHubSecretScanningCustomPatternValidationErrorCode decoded =
                GitHubSecretScanningCustomPatternValidationErrorCode.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown =
                catchThrowable(() -> GitHubSecretScanningCustomPatternValidationErrorCode.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(Arguments.of(null, NullPointerException.class, "value"));
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubSecretScanningCustomPatternValidationErrorCode decoded =
                GitHubSecretScanningCustomPatternValidationErrorCode.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubSecretScanningCustomPatternValidationErrorCode.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
