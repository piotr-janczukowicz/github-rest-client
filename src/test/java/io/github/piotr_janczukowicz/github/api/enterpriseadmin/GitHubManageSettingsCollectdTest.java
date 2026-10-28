package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSettingsCollectdTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageSettingsCollectd value = EnterpriseAdminCatalogFixtures.manageSettingsCollectd();

        assertThat(value.toString()).contains("password=<redacted>").doesNotContain("password=password");
    }
}
