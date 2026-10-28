package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubRunnerGroupVisibilityForEnterpriseTest {

    @ParameterizedTest
    @CsvSource({"selected,SELECTED", "all,ALL"})
    void fromWire_shouldReturnConstant_whenValueKnown(
            final String wire, final GitHubRunnerGroupVisibilityForEnterprise expected) {

        final GitHubRunnerGroupVisibilityForEnterprise decoded =
                GitHubRunnerGroupVisibilityForEnterprise.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(expected);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubRunnerGroupVisibilityForEnterprise.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubRunnerGroupVisibilityForEnterprise.fromWire("private"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
