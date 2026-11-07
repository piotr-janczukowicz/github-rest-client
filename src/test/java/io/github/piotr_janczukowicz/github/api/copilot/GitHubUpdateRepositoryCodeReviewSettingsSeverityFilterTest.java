package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubUpdateRepositoryCodeReviewSettingsSeverityFilterTest {

    @ParameterizedTest
    @CsvSource({"no_filter,NO_FILTER", "low,LOW", "low_and_medium,LOW_AND_MEDIUM"})
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter value) {
        final GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter decoded =
                GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter.fromWire("other"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("other");
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateRepositoryCodeReviewSettingsSeverityFilter.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
