package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubCodeQualityFindingRuleSeverityTest {

    @ParameterizedTest
    @CsvSource({"error,ERROR", "warning,WARNING", "note,NOTE", "none,NONE"})
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubCodeQualityFindingRuleSeverity value) {
        final GitHubCodeQualityFindingRuleSeverity decoded = GitHubCodeQualityFindingRuleSeverity.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeQualityFindingRuleSeverity.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubCodeQualityFindingRuleSeverity decoded = GitHubCodeQualityFindingRuleSeverity.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubCodeQualityFindingRuleSeverity.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
