package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsGistsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsGists value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsGists();

        assertThat(value.totalGists()).isEqualTo(1);
        assertThat(value.privateGists()).isEqualTo(1);
        assertThat(value.publicGists()).isEqualTo(1);
    }
}
