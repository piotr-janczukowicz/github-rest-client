package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckLicenseComplianceForPackagesPurlResultTest {

    @Test
    void gitHubCheckLicenseComplianceForPackagesPurlResult_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCheckLicenseComplianceForPackagesPurlResult value =
                DependencyGraphCatalogFixtures.checkLicenseComplianceForPackagesPurlResult();

        assertThat(value.status()).isEqualTo(GitHubCheckLicenseComplianceForPackagesPurlResultStatus.ALLOWED);
        assertThat(value.license()).isEqualTo("MIT");
        assertThat(value.reason()).isEqualTo(GitHubCheckLicenseComplianceForPackagesPurlResultReason.ALLOWED_LICENSE);
    }
}
