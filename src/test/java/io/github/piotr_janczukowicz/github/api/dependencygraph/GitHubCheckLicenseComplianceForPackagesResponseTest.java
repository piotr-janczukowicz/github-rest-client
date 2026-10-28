package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckLicenseComplianceForPackagesResponseTest {

    @Test
    void gitHubCheckLicenseComplianceForPackagesResponse_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCheckLicenseComplianceForPackagesResponse value =
                DependencyGraphCatalogFixtures.checkLicenseComplianceForPackagesResponse();

        assertThat(value.purls())
                .isEqualTo(Map.of(
                        "pkg:npm/left-pad@1.3.0",
                        DependencyGraphCatalogFixtures.checkLicenseComplianceForPackagesPurlResult()));
    }
}
