package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsCommentsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsComments value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsComments();

        assertThat(value.totalCommitComments()).isEqualTo(1);
        assertThat(value.totalGistComments()).isEqualTo(1);
        assertThat(value.totalIssueComments()).isEqualTo(1);
        assertThat(value.totalPullRequestComments()).isEqualTo(1);
    }
}
