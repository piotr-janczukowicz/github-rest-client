package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsPackagesEcosystemTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsPackagesEcosystem value =
                EnterpriseAdminCatalogFixtures.serverStatisticsPackagesEcosystem();

        assertThat(value.name()).isEqualTo(GitHubServerStatisticsPackagesEcosystemName.NPM);
        assertThat(value.enabled()).isEqualTo(GitHubServerStatisticsPackagesEcosystemEnabled.TRUE);
        assertThat(value.publishedPackagesCount()).isEqualTo(1);
        assertThat(value.privatePackagesCount()).isEqualTo(1);
        assertThat(value.publicPackagesCount()).isEqualTo(1);
        assertThat(value.internalPackagesCount()).isEqualTo(1);
        assertThat(value.userPackagesCount()).isEqualTo(1);
        assertThat(value.organizationPackagesCount()).isEqualTo(1);
        assertThat(value.dailyDownloadCount()).isEqualTo(1);
        assertThat(value.dailyUpdateCount()).isEqualTo(1);
        assertThat(value.dailyDeleteCount()).isEqualTo(1);
        assertThat(value.dailyCreateCount()).isEqualTo(1);
    }
}
