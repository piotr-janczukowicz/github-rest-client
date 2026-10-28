package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsOrgsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsOrgs value = EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsOrgs();

        assertThat(value.totalOrgs()).isEqualTo(1);
        assertThat(value.disabledOrgs()).isEqualTo(1);
        assertThat(value.totalTeams()).isEqualTo(1);
        assertThat(value.totalTeamMembers()).isEqualTo(1);
    }
}
