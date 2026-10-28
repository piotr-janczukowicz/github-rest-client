package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetLicenseSyncStatusServerInstanceTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetLicenseSyncStatusServerInstance value =
                EnterpriseAdminCatalogFixtures.getLicenseSyncStatusServerInstance();

        assertThat(value.serverId()).isEqualTo("sample-server_id");
        assertThat(value.hostname()).isEqualTo("sample-hostname");
        assertThat(value.lastSync()).isNotNull();
    }
}
