package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsPackagesEcosystemEnabledTest {

    @Test
    void fromWire_shouldReturnTrue_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemEnabled.fromWire("TRUE"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemEnabled.TRUE);
    }

    @Test
    void fromWire_shouldReturnFalse_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemEnabled.fromWire("FALSE"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemEnabled.FALSE);
    }

    @Test
    void fromWire_shouldReturnReadonly_whenKnownValue() {
        assertThat(GitHubServerStatisticsPackagesEcosystemEnabled.fromWire("READONLY"))
                .isEqualTo(GitHubServerStatisticsPackagesEcosystemEnabled.READONLY);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubServerStatisticsPackagesEcosystemEnabled decoded =
                GitHubServerStatisticsPackagesEcosystemEnabled.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubServerStatisticsPackagesEcosystemEnabled.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
