package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckLicenseComplianceForPackagesPayloadTest {

    @Test
    void gitHubCheckLicenseComplianceForPackagesPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCheckLicenseComplianceForPackagesPayload value =
                DependencyGraphCatalogFixtures.checkLicenseComplianceForPackagesPayload();

        assertThat(value.purls()).isEqualTo(List.of("pkg:npm/left-pad@1.3.0"));
    }
}
