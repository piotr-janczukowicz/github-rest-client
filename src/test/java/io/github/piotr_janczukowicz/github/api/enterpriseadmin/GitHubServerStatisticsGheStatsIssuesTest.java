package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsIssuesTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsIssues value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsIssues();

        assertThat(value.totalIssues()).isEqualTo(1);
        assertThat(value.openIssues()).isEqualTo(1);
        assertThat(value.closedIssues()).isEqualTo(1);
    }
}
