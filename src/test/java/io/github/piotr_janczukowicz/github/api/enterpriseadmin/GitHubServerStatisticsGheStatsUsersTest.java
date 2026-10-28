package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsUsersTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsUsers value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsUsers();

        assertThat(value.totalUsers()).isEqualTo(1);
        assertThat(value.adminUsers()).isEqualTo(1);
        assertThat(value.suspendedUsers()).isEqualTo(1);
    }
}
