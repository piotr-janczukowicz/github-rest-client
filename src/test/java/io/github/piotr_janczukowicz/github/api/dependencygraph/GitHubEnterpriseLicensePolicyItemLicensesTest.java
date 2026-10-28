package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseLicensePolicyItemLicensesTest {

    @Test
    void gitHubEnterpriseLicensePolicyItemLicenses_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubEnterpriseLicensePolicyItemLicenses value =
                DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemLicenses();

        assertThat(value.allowed())
                .isEqualTo(List.of(DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemLicense()));
    }
}
