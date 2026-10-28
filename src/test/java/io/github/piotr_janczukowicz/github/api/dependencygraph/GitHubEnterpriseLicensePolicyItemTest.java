package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseLicensePolicyItemTest {

    @Test
    void gitHubEnterpriseLicensePolicyItem_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubEnterpriseLicensePolicyItem value = DependencyGraphCatalogFixtures.enterpriseLicensePolicyItem();

        assertThat(value.licenses()).isEqualTo(DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemLicenses());
        assertThat(value.packages())
                .isEqualTo(List.of(DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemPackage()));
    }
}
