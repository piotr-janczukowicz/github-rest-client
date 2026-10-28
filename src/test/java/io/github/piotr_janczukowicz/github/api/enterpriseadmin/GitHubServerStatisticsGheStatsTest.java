package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGheStatsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGheStats value = EnterpriseAdminCatalogFixtures.serverStatisticsGheStats();

        assertThat(value.comments()).isNotNull();
        assertThat(value.gists()).isNotNull();
        assertThat(value.hooks()).isNotNull();
        assertThat(value.issues()).isNotNull();
        assertThat(value.milestones()).isNotNull();
        assertThat(value.orgs()).isNotNull();
        assertThat(value.pages()).isNotNull();
        assertThat(value.pulls()).isNotNull();
        assertThat(value.repos()).isNotNull();
        assertThat(value.users()).isNotNull();
    }
}
