package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatistics value = EnterpriseAdminCatalogFixtures.serverStatistics();

        assertThat(value.serverId()).isEqualTo("sample-server_id");
        assertThat(value.collectionDate()).isEqualTo("sample-collection_date");
        assertThat(value.schemaVersion()).isEqualTo("sample-schema_version");
        assertThat(value.ghesVersion()).isEqualTo("sample-ghes_version");
        assertThat(value.hostName()).isEqualTo("sample-host_name");
        assertThat(value.githubConnect()).isNotNull();
        assertThat(value.gheStats()).isNotNull();
        assertThat(value.dormantUsers()).isNotNull();
        assertThat(value.actionsStats()).isNotNull();
        assertThat(value.packagesStats()).isNotNull();
    }
}
