package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSettingsSmtpTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageSettingsSmtp value = EnterpriseAdminCatalogFixtures.manageSettingsSmtp();

        assertThat(value.toString()).contains("password=<redacted>").doesNotContain("password=password");
    }
}
