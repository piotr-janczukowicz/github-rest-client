package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseLicensePolicyResponseTest {

    @Test
    void gitHubEnterpriseLicensePolicyResponse_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubEnterpriseLicensePolicyResponse value =
                DependencyGraphCatalogFixtures.enterpriseLicensePolicyResponse();

        assertThat(value.createdAt()).isEqualTo(Instant.parse("2026-01-01T00:00:00Z"));
        assertThat(value.licenses()).isEqualTo(DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemLicenses());
        assertThat(value.packages())
                .isEqualTo(List.of(DependencyGraphCatalogFixtures.enterpriseLicensePolicyItemPackage()));
    }

    @Test
    void constructor_shouldLeaveLicensesNull_whenNull() {
        final GitHubEnterpriseLicensePolicyResponse value = GitHubEnterpriseLicensePolicyResponseBuilder.builder(
                        DependencyGraphCatalogFixtures.enterpriseLicensePolicyResponse())
                .licenses(null)
                .build();

        assertThat(value.licenses()).isNull();
    }

    @Test
    void constructor_shouldLeavePackagesNull_whenNull() {
        final GitHubEnterpriseLicensePolicyResponse value = GitHubEnterpriseLicensePolicyResponseBuilder.builder(
                        DependencyGraphCatalogFixtures.enterpriseLicensePolicyResponse())
                .packages(null)
                .build();

        assertThat(value.packages()).isNull();
    }
}
