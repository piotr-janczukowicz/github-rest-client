package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsPullsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStatsPulls value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGheStatsPulls();

        assertThat(value.totalPulls()).isEqualTo(1);
        assertThat(value.mergedPulls()).isEqualTo(1);
        assertThat(value.mergeablePulls()).isEqualTo(1);
        assertThat(value.unmergeablePulls()).isEqualTo(1);
    }
}
