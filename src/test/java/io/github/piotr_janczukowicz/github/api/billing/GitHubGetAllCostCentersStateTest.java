package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubGetAllCostCentersStateTest {

    @ParameterizedTest
    @CsvSource({"active,ACTIVE", "deleted,DELETED"})
    void fromWire_shouldReturnValue_whenKnownValue(final String wire, final GitHubGetAllCostCentersState value) {
        final GitHubGetAllCostCentersState decoded = GitHubGetAllCostCentersState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetAllCostCentersState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrow_whenValueNotKnown() {
        final Throwable thrown = catchThrowable(() -> GitHubGetAllCostCentersState.fromWire("brand_new_value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
