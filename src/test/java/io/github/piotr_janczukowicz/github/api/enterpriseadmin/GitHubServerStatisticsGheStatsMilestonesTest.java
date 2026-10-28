package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsMilestonesTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsMilestones value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsMilestones();

        assertThat(value.totalMilestones()).isEqualTo(1);
        assertThat(value.openMilestones()).isEqualTo(1);
        assertThat(value.closedMilestones()).isEqualTo(1);
    }
}
