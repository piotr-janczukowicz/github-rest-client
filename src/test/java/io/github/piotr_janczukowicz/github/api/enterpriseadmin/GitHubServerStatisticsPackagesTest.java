package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsPackagesTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsPackages value = EnterpriseAdminCatalogFixtures.serverStatisticsPackages();

        assertThat(value.registryEnabled()).isTrue();
        assertThat(value.registryV2Enabled()).isTrue();
        assertThat(value.ecosystems()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveEcosystemsNull_whenNull() {
        final GitHubServerStatisticsPackages value = GitHubServerStatisticsPackagesBuilder.builder(
                        EnterpriseAdminCatalogFixtures.serverStatisticsPackages())
                .ecosystems(null)
                .build();

        assertThat(value.ecosystems()).isNull();
    }
}
