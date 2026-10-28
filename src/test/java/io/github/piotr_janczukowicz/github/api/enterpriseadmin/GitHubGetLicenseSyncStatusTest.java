package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetLicenseSyncStatusTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetLicenseSyncStatus value = EnterpriseAdminCatalogFixtures.getLicenseSyncStatus();

        assertThat(value.serverInstances()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveServerInstancesNull_whenNull() {
        final GitHubGetLicenseSyncStatus value = GitHubGetLicenseSyncStatusBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getLicenseSyncStatus())
                .serverInstances(null)
                .build();

        assertThat(value.serverInstances()).isNull();
    }
}
