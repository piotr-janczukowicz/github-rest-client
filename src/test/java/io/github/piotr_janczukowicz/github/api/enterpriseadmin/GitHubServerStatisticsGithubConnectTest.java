package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsGithubConnectTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsGithubConnect value =
                EnterpriseAdminCatalogFixtures.serverStatisticsGithubConnect();

        assertThat(value.featuresEnabled()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveFeaturesEnabledNull_whenNull() {
        final GitHubServerStatisticsGithubConnect value = GitHubServerStatisticsGithubConnectBuilder.builder(
                        EnterpriseAdminCatalogFixtures.serverStatisticsGithubConnect())
                .featuresEnabled(null)
                .build();

        assertThat(value.featuresEnabled()).isNull();
    }
}
