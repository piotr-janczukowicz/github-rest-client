package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubServerStatisticsDormantUsersTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubServerStatisticsDormantUsers value = EnterpriseAdminCatalogFixtures.serverStatisticsDormantUsers();

        assertThat(value.totalDormantUsers()).isEqualTo(1);
        assertThat(value.dormancyThreshold()).isEqualTo("sample-dormancy_threshold");
    }
}
