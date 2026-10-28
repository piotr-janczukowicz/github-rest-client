package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSettingsLdapTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubManageSettingsLdap value = EnterpriseAdminCatalogFixtures.manageSettingsLdap();

        assertThat(value.toString()).contains("password=<redacted>").doesNotContain("password=password");
    }
}
