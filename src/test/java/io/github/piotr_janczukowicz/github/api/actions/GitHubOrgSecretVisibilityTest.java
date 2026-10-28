package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubOrgSecretVisibilityTest {

    @ParameterizedTest
    @CsvSource({"all,ALL", "private,PRIVATE", "selected,SELECTED"})
    void fromWire_shouldReturnConstant_whenValueKnown(final String wire, final GitHubOrgSecretVisibility expected) {

        final GitHubOrgSecretVisibility decoded = GitHubOrgSecretVisibility.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(expected);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrgSecretVisibility.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubOrgSecretVisibility.fromWire("unknown-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
