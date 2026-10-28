package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseLicensePolicyItemPackageTest {

    @Test
    void gitHubEnterpriseLicensePolicyItemPackage_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubEnterpriseLicensePolicyItemPackage value =
                DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemPackage();

        assertThat(value.packageManager()).isEqualTo(GitHubEnterpriseLicensePolicyItemPackageManager.NPM);
        assertThat(value.name()).isEqualTo("left-pad");
        assertThat(value.action()).isEqualTo(GitHubEnterpriseLicensePolicyItemPackageAction.ALLOWED);
        assertThat(value.reason()).isEqualTo("Approved by the security team.");
        assertThat(value.extraAllowedSpdxIds()).isEqualTo(List.of("Apache-2.0"));
        assertThat(value.addedAt()).isEqualTo(Instant.parse("2026-01-02T00:00:00Z"));
    }
}
