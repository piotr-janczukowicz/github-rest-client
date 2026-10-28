package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsReposTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsRepos value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsRepos();

        assertThat(value.totalRepos()).isEqualTo(1);
        assertThat(value.rootRepos()).isEqualTo(1);
        assertThat(value.forkRepos()).isEqualTo(1);
        assertThat(value.orgRepos()).isEqualTo(1);
        assertThat(value.totalPushes()).isEqualTo(1);
        assertThat(value.totalWikis()).isEqualTo(1);
    }
}
