package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubManageUpgradeStatusPreUpgradeStatusTest {

    @ParameterizedTest
    @CsvSource({"not_started,NOT_STARTED", "in_progress,IN_PROGRESS", "completed,COMPLETED", "failed,FAILED"})
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubManageUpgradeStatusPreUpgradeStatus value) {
        final GitHubManageUpgradeStatusPreUpgradeStatus decoded =
                GitHubManageUpgradeStatusPreUpgradeStatus.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubManageUpgradeStatusPreUpgradeStatus.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubManageUpgradeStatusPreUpgradeStatus decoded =
                GitHubManageUpgradeStatusPreUpgradeStatus.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubManageUpgradeStatusPreUpgradeStatus.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
