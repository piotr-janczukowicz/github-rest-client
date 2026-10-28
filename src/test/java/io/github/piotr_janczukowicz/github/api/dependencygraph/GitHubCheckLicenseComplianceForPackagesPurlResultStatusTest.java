package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubCheckLicenseComplianceForPackagesPurlResultStatusTest {

    @ParameterizedTest
    @CsvSource({"allowed,ALLOWED", "denied,DENIED", "unknown,UNKNOWN"})
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubCheckLicenseComplianceForPackagesPurlResultStatus value) {
        final GitHubCheckLicenseComplianceForPackagesPurlResultStatus decoded =
                GitHubCheckLicenseComplianceForPackagesPurlResultStatus.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubCheckLicenseComplianceForPackagesPurlResultStatus decoded =
                GitHubCheckLicenseComplianceForPackagesPurlResultStatus.fromWire("not-a-value");

        assertThat(decoded).isEqualTo(GitHubCheckLicenseComplianceForPackagesPurlResultStatus.UNKNOWN);
    }
}
