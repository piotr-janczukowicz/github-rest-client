package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsHooksTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsHooks value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsHooks();

        assertThat(value.totalHooks()).isEqualTo(1);
        assertThat(value.activeHooks()).isEqualTo(1);
        assertThat(value.inactiveHooks()).isEqualTo(1);
    }
}
