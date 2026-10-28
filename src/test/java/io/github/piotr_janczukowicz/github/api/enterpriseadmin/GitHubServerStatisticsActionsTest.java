package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsActionsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsActions value = EnterpriseAdminCatalogFixtures.serverStatisticsActions();

        assertThat(value.numberOfReposUsingActions()).isEqualTo(1);
        assertThat(value.percentageOfReposUsingActions()).isEqualTo("sample-percentage_of_repos_using_actions");
    }
}
