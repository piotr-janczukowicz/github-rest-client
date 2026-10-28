package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSettingsTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageSettings value = EnterpriseAdminCatalogFixtures.manageSettings();

        assertThat(value.toString())
                .contains("adminPassword=<redacted>")
                .doesNotContain("adminPassword=admin_password");
    }
}
