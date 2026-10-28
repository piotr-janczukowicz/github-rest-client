package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsPagesTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsPages value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsPages();

        assertThat(value.totalPages()).isEqualTo(1);
    }
}
