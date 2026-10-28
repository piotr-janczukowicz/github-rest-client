package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseLicensePolicyItemLicenseTest {

    @Test
    void gitHubEnterpriseLicensePolicyItemLicense_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubEnterpriseLicensePolicyItemLicense value =
                DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemLicense();

        assertThat(value.spdxId()).isEqualTo("MIT");
        assertThat(value.addedAt()).isEqualTo(Instant.parse("2026-01-01T00:00:00Z"));
    }
}
