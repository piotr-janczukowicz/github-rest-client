package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubCheckLicenseComplianceForPackagesPurlResultReasonTest {

    @ParameterizedTest
    @CsvSource({
        "allowed_license,ALLOWED_LICENSE",
        "allowed_package,ALLOWED_PACKAGE",
        "denied_license,DENIED_LICENSE",
        "unsupported_package_ecosystem,UNSUPPORTED_PACKAGE_ECOSYSTEM",
        "unknown,UNKNOWN"
    })
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubCheckLicenseComplianceForPackagesPurlResultReason value) {
        final GitHubCheckLicenseComplianceForPackagesPurlResultReason decoded =
                GitHubCheckLicenseComplianceForPackagesPurlResultReason.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubCheckLicenseComplianceForPackagesPurlResultReason decoded =
                GitHubCheckLicenseComplianceForPackagesPurlResultReason.fromWire("not-a-value");

        assertThat(decoded).isEqualTo(GitHubCheckLicenseComplianceForPackagesPurlResultReason.UNKNOWN);
    }
}
